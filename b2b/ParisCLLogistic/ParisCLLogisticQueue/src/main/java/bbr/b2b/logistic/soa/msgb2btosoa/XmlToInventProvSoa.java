package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.buyorders.report.classes.UpdateVeVAvailableStockInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAvailableStockReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAvailableStockReportResultByItemDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAvailableStockReportResultDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemVevReportDataDTO;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;

/*
 * Clase que maneja el procesamiento del mensaje via SOA de gestión de Inventario de proveedores
 * Seg�n CU 149
 */
@Stateless(name = "handlers/XmlToInventProvSoa")
public class XmlToInventProvSoa implements XmlToInventProvSoaLocal {

	private static Logger logger = Logger.getLogger("SOALog");
	//private static Logger loggerC = Logger.getLogger(XmlToInventProvSoa.class);
	
	private ArrayList<String> errorList = new ArrayList<String>();
	Map<String, VeVAvailableStockReportResultByItemDTO> resultBySku = new HashMap<String, VeVAvailableStockReportResultByItemDTO>();
	
	private static JAXBContext jc = null;
	
	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	private VendorItemServerLocal vendoritemserver;


	@EJB
	BuyOrderReportManagerLocal buyOrderManager; 

	@EJB
	SchedulerManagerLocal schedulermanager;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RespuestaTicket");
		return jc;
	}
	
	private void doValidateSoaMessage(InventarioProveedor message) throws LoadDataException{
		String msg = "";
		// NOMBRE PORTAL
		if (message.getNombreportal() == null || message.getNombreportal().trim().equals("")){
			msg =  "Nombre de Portal Incorrecto";
			errorList.add(msg);
			logger.error(msg);
		}

		// CODIGO comprador
		if (message.getComprador() == null || message.getComprador().getRut() == null || message.getComprador().getRut().trim().equals("")){
			logger.error("Error actualizado stock. No hay datos para Codigo Comprador");
			throw new LoadDataException("Error actualizado stock. No hay datos para Codigo Comprador");
		}


		// DETALLES NO PUEDE ESTAR VACIO
		if (message.getDetalles() == null || message.getDetalles().getDetalle() == null || message.getDetalles().getDetalle().size() <1  ){
			msg =  "No hay datos para el Inventario";
			errorList.add(msg);
			logger.error(msg);			
		}	
		
		// VERIFICA QUE EL PORTAL CORRESPONDA
		String nombrePortal;
		String codigoPortal;

		codigoPortal = B2BSystemPropertiesUtil.getProperty("CodigoPortal");
		nombrePortal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
		String nombrePortalMsg = message.getNombreportal();			
		if (!nombrePortal.equalsIgnoreCase(nombrePortalMsg) && !codigoPortal.equalsIgnoreCase(nombrePortalMsg)){
			msg =  "El nombre de portal no corresponde";
			errorList.add(msg);
			logger.error(msg);	
		}

		
		if (errorList.size() >0)
			throw new LoadDataException("Existen datos mal formados en el XML para Actualizar Inventario");

	}



	public void processMessage(InventarioProveedor message, Long ticketNumber) throws ServiceException {
		
		RegionalLogisticConstants constants = RegionalLogisticConstants.getInstance();
		String codigoportal = B2BSystemPropertiesUtil.getProperty("CodigoPortal");		
		
		logger.info("Inicio proceso archivo de Inventario");
		errorList.clear();
		UpdateVeVAvailableStockInitParamDTO initParams = null;
		VendorW vendor = null;
		String msg = "";
		
		// Verificar si existe vendedor correctamente si no viene generar una excepcion
		if (message.getVendedor() == null || message.getVendedor().getCodigo() == null || message.getVendedor().getCodigo().trim().equals("")){
			msg =  "No hay datos para Codigo Vendedor";
			errorList.add(msg);
			logger.error(msg);
			sendMailToBBR("Ha Ocurrido un error al procesar un mensaje de actualización de stock: No existe información de Vendor");
			throw new LoadDataException("No se define un vendedor en el XML");
		}
		else{
			String codProveedor = message.getVendedor().getCodigo().trim();
			
			try {
				VendorW[]  vendorArr = vendorserver.getByPropertyAsArray("code", codProveedor);				
				if (vendorArr == null || vendorArr.length == 0){
					sendMailToBBR("Ha Ocurrido un error al procesar un mensaje de actualización de stock: No existe información de Vendor");
					throw new LoadDataException("No existe proveedor con código " + codProveedor);
				}
				vendor = vendorArr[0];
			} catch (NotFoundException nfe) {
				nfe.printStackTrace();
				sendMailToBBR("Ha Ocurrido un error al procesar un mensaje de actualización de stock: No existe información de Vendor");
				throw new LoadDataException("No existe Proveedor con código " + codProveedor);
			} 
			catch (Exception ge) {
				ge.printStackTrace();
				sendMailToBBR("Ha Ocurrido un error al procesar un mensaje de actualización de stock: No existe información de Vendor");
				throw new LoadDataException("Problemas al obtener el Proveedor" + codProveedor);
			} 			
		}
	
		//Validar los datos principales en caso de error enviar un mail al vendedor si existe
		try {
			doValidateSoaMessage(message);
		} catch (Exception e) {					
			if (vendor.getEmail()!= null && !vendor.getEmail().isEmpty()){
				String mailcontent = getXMLErrorsContentMail();	
				sendMailToVendor(mailcontent, vendor);				
			}
			throw new LoadDataException("Problemas validar el XML");			
		}
		

		// PROVEEDOR
		Long vendorId = vendor.getId();

		//VeVAvailableStockReportDataDTO
		//Mapa para sumarizar los articulos para que todas las cantidades de un producto queden totalizadas
		List<Detalle> inventarioprov = message.getDetalles().getDetalle();

		HashMap<String, VeVAvailableStockReportDataDTO> inventDetailmap = new HashMap<String, VeVAvailableStockReportDataDTO>();
		logger.info("Intentando actualizar inventario para: "+vendor.getName());
		List<String> prodwithoutskuEANS = new ArrayList<String>();
		for(Detalle detail : inventarioprov){	
			try {				
				// Si no existe en el mapa agregarlo sino sumarizar la cantidad
				
				if(detail.getSku() == null || detail.getSku().trim().length() == 0){
					prodwithoutskuEANS.add(detail.getEan());
					continue;
				}
				
				if (inventDetailmap.containsKey(detail.getSku())){				
					Integer cantidad = Integer.valueOf(detail.getCantidad());
					inventDetailmap.get(detail.getSku()).setStock(inventDetailmap.get(detail.getSku()).getStock() +  cantidad );
					inventDetailmap.get(detail.getSku()).setStockoriginal(inventDetailmap.get(detail.getSku()).getStock());			//se setea el stock envado por el cliente
				}
				else{
					VeVAvailableStockReportDataDTO newstockVev = getVeVAvailableStockReportDataDTOFromDetalle(detail,vendorId );
					if(newstockVev != null){
						inventDetailmap.put(newstockVev.getItemsku(), newstockVev);
					}				
				}				
			}
			catch (NumberFormatException e) {
				msg =  "Analizando el item: " + detail.getSku() + "  La cantidad no es num�rica";
				errorList.add(msg);
				resultBySku.put(detail.getSku(), new VeVAvailableStockReportResultByItemDTO(detail.getSku(), msg, "-1"));
				logger.error(msg);	
				e.printStackTrace();
			}	
			catch (Exception e) {
				msg =  "ocurrió un error intentando actualizar el stock, analizando el item: " + detail.getSku() + "\n" + e.getMessage();
				errorList.add(msg);
				resultBySku.put(detail.getSku(), new VeVAvailableStockReportResultByItemDTO(detail.getSku(), msg, "-1"));
				logger.error(msg);	
				e.printStackTrace();
			}			
		}
		if(prodwithoutskuEANS.size() > 0) {
			for (String ean : prodwithoutskuEANS) {
				msg = "EAN: " + ean + " no se est� informando el código SKU";
				errorList.add(msg);
			}
		}
		logger.info("Intentando actualizar inventario para: "+vendor.getName()+ ", Cantidad de Productos: "+inventDetailmap.size());

		int resultSoa = -1;
		String resulttxt = "";
		VeVAvailableStockReportResultDTO resultUpdate =  new VeVAvailableStockReportResultDTO();
		
		
		//Preparar la llamada al servicio para actualizar el inventario
		if (inventDetailmap.size() > 0){
			initParams = new UpdateVeVAvailableStockInitParamDTO();

			VeVAvailableStockReportDataDTO[] stockArr = new  VeVAvailableStockReportDataDTO[inventDetailmap.size()];
			stockArr = inventDetailmap.values().toArray(stockArr);			 
			initParams.setData(stockArr);
			initParams.setVendorcode(vendor.getRut());
			initParams.setUserenterprisecode("77575650");
			initParams.setUserenterprisename("BBR SERVICIOS LTDA");
			initParams.setUsername("B2B Link");
			initParams.setUsertype("Sistema");
			
			for(int  i = 0;i<stockArr.length;i++){
				logger.info("Intentando actualizar inventario para: "+vendor.getName()+ ",Preparando para actualizar producto: "+stockArr[i].getItemsku());
			}
			try {
				resultUpdate = buyOrderManager.doUpdateVeVAvailableStock(initParams);
			} catch (Exception e) {
				resultUpdate = new VeVAvailableStockReportResultDTO();
				RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultUpdate, "L1");
				resulttxt = "Error Intentando realizar doUpdateVeVAvailableStock";
				logger.error(resulttxt);
				e.printStackTrace();
			}
			if(resultUpdate != null){
				resultSoa = 0;	//�xito;
				resulttxt = "Se ejecuto la actualizacion de stock VeV con los siguientes resultados:"
					+"\nStock actualizado: "+((resultUpdate.getStockreport() != null)?resultUpdate.getStockreport().length:"no se tienen registros")
					+"\nErrores registrados: "+((resultUpdate.getValidationerrors() != null)?resultUpdate.getValidationerrors().length:"no se tienen registros");
				logger.info(resulttxt);
			}
			
			// Agregado para enviar mail a vendedor
			logger.info("Enviando correo al proveedor");
			String emailC = getResultContentMail(resultUpdate,vendor);
			sendMailToVendor(emailC,vendor);
		 
		}
		else 
		{
			//Loguear que no hubo datos para actualizar el inventario
			String emailC = getResultContentMail(new VeVAvailableStockReportResultDTO() ,vendor);
			sendMailToVendor(emailC,vendor);
			resulttxt = "No se encontraron elementos que pudieran actualizarse en el stock";
			logger.info(resulttxt);
		
			resultSoa = 0;	//�xito;

		}

		//RESPUESTA HACIA SOA
		ObjectFactory objFactory = new ObjectFactory();
		RespuestaTicket qrespuestaticket = objFactory.createRespuestaTicket();
		qrespuestaticket.setTicketnumber(ticketNumber.longValue());
		qrespuestaticket.setCodproveedor(vendor.getCode());
		qrespuestaticket.setNombreportal(codigoportal);
		qrespuestaticket.setServicename("AST");
		qrespuestaticket.setEstadoticket(String.valueOf(resultUpdate.getStatuscode()));
		qrespuestaticket.setDescripcion(resultUpdate.getStatusmessage());
		
		//se completan detalles
		Detalles detallesticket = objFactory.createRespuestaTicketDetalles();
		List<bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle> detalleticketlist = detallesticket.getDetalle();
		VeVAvailableStockReportResultByItemDTO[] resultserror = resultUpdate.getStockreportbyitem();

		//se agregan los resultados de WS
		if(resultserror != null){
			for (int i = 0; i < resultserror.length; i++) {
				resultBySku.put(resultserror[i].getSku(), resultserror[i]);
			}
		}
		
		Iterator<String> it = resultBySku.keySet().iterator();
		String key = "";
		while(it.hasNext()){
			key = it.next();
			bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle detalleticket = objFactory.createRespuestaTicketDetallesDetalle();
			detalleticket.setTipo("sku");
			detalleticket.setCodigo(resultBySku.get(key).getSku());
			detalleticket.setEstado(resultBySku.get(key).getState());
			detalleticket.setDescripcion(resultBySku.get(key).getDetail());
			detalleticketlist.add(detalleticket);
		}
		qrespuestaticket.setDetalles(detallesticket);
		
		try {
			// Obtiene string XML para enviarlo a la cola
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qrespuestaticket, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE			
			doBackUpMessage(result, String.valueOf(qrespuestaticket.getTicketnumber()), "SOA");

			try {			
				schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/q_esbgrlcenco", "RespuestaTicket", String.valueOf(ticketNumber), result);
			} catch (Exception ex) {
				ex.printStackTrace();
				//NO PUEDE HABER RECOVERY PORQUE SE PIERDEN LOS HEADER
				// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
//				MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
//				String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_SOA_" + qactualizacioninventario.getTicketnumber();
//				try {
//					msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
//				} catch (Exception e1) {
//					logger.debug(e1.getLocalizedMessage());
//				}
			}
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		logger.info("Fin proceso archivo de Inventario");
	}

	/*
	 * Mapea al wraper que maneja los servicios
	 */
	private VeVAvailableStockReportDataDTO getVeVAvailableStockReportDataDTOFromDetalle(Detalle detail,Long vendorId ){
		VeVAvailableStockReportDataDTO result = null;

		if(detail != null){
			// buscar el vendorItem en el b2b  que sean Vev
			try {
				VendorItemVevReportDataDTO vendorItem = vendoritemserver.getVendorItemByVendorAndSku(vendorId,detail.getSku());
				if(vendorItem != null){
					result = new VeVAvailableStockReportDataDTO();
					result.setItemsku(detail.getSku());
					result.setStock( Integer.valueOf(detail.getCantidad()));
					result.setStockoriginal(Integer.valueOf(detail.getCantidad()));
					result.setItemdescription(vendorItem.getDescription());
					result.setVendorcode(vendorItem.getVendoritemcode());
				}
				else{
					String msg =  "Analizando el item: " + detail.getSku() +"  No existe el item para el proveedor en venta en verde";
					errorList.add(msg);
					resultBySku.put(detail.getSku(), new VeVAvailableStockReportResultByItemDTO(detail.getSku(), msg, "-1"));
					logger.error(msg);						
				}
			}
			catch (NumberFormatException e) {
				String msg =  "Analizando el item: " + detail.getSku() +"  La cantidad no es num�rica";
				errorList.add(msg);
				resultBySku.put(detail.getSku(), new VeVAvailableStockReportResultByItemDTO(detail.getSku(), msg, "-1"));
				logger.error(msg);		
			} 
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}	
		}

		return result;		

	}

	
	private String getXMLErrorsContentMail(){
		String content = errorList.size() > 0 ? "Ocurrieron Errores al Intentar cargar los datos del XML  \n " :   "";		
		
		for(String error : errorList){
			content = content  + error + " \n";	
		}		
		return content;
	}
	
	private String getResultContentMail(VeVAvailableStockReportResultDTO reportResult, VendorW vendor){	
		
		//boolean success  = false;
		boolean errors = false;
		String content = "";
		
		// En caso de que invocar el servicio
		if (reportResult == null){
			return "No se obtuvo resultados al intentar actualizar el stock.";
		}
//		else{
//			if(!reportResult.getStatuscode().equals("0")){
//				errors = true;
//			}else{
//				// Si existen errores
//				errors = (reportResult.getValidationerrors() != null && reportResult.getValidationerrors().length > 0); 
//			}
//		}
		
		if(!reportResult.getStatuscode().equals("0")){
			errors = true;
			logger.info("Existen errores: "+reportResult.getStatuscode()+" "+reportResult.getStatusmessage());
		}else{
			if(reportResult.getStockreport() != null && reportResult.getStockreport().length > 0){
				content = content + " <br>" + 
				  " Se ha realizado exitosamente la actualización de capacidad disponible para VeV con el siguiente detalle: <br><br>";
						
						Date now = new Date();
						content = content + " Fecha y hora de la actualización:  "+  now.toString() + " <br>";
						content = content + " Proveedor que actualizo la información: " + vendor.getTradename() + " <br>";
						content = content + " Tipo de actualización: Capacidad Disponible VeV" + " <br>";
						content = content + " Productos actualizados:" + " <br><br>";
						for ( VeVAvailableStockReportDataDTO stockitem : reportResult.getStockreport()){				
							String detailStr =  " Item SKU: " + stockitem.getItemsku() + "<br>" +
												" código: <br>" + stockitem.getVendorcode() + "<br>" +
												" Descripción: <br>" + stockitem.getItemdescription() + "<br>" +
												" Cantidad  " + stockitem.getStockoriginal() + "<br>";
							content = content + detailStr + " <br>";				
				}
			}else{
				content = content + " <br>" + 
				  " Se ha realizado exitosamente la actualización de capacidad disponible para VeV con el siguiente detalle: <br><br>";
				content = content + "No hay nuevos cambios de stock <br>";
			}
		}
		
		// Si hay existo actualizando algo
		//success = (reportResult.getStockreport() != null && reportResult.getStockreport().length > 0 );	
		

//		//Caso exito.
//		if(success){
//			content = content + " <br>" + 
//					  " Se ha realizado exitosamente la actualización de capacidad disponible para VeV con el siguiente detalle: <br><br>";
//			
//			Date now = new Date();
//			content = content + " Fecha y hora de la actualización:  "+  now.toString() + " <br>";
//			content = content + " Proveedor que actualizo la información: " + vendor.getTradename() + " <br>";
//			content = content + " Tipo de actualización: Capacidad Disponible VeV" + " <br>";
//			content = content + " Productos actualizados:" + " <br>";
//			for ( VeVAvailableStockReportDataDTO stockitem : reportResult.getStockreport()){				
//				String detailStr =  " Item SKU: " + stockitem.getItemsku() + "<br>" +
//									" código: <br>" + stockitem.getVendorcode() + "<br>" +
//									" Descripción: <br>" + stockitem.getItemdescription() + "<br>" +
//									" Cantidad  " + stockitem.getStock() + "<br>";
//				content = content + detailStr + " <br>";				
//			}			
//		}
		
		//
		if(errors || errorList.size() > 0  || (reportResult.getValidationerrors() != null && reportResult.getValidationerrors().length > 0)){
			content = content + " <br>" + 
					  "Han ocurrido errores en la actualización de capacidad disponible para VeV con el siguiente detalle: " + " <br><br>";
			if(reportResult.getValidationerrors() != null){
				for ( BaseResultDTO error : reportResult.getValidationerrors()){				
					String detailStr =  error.getStatusmessage();
					content = content + detailStr + " <br>";
				}
			}
			if(errorList != null){
				for ( String errorXML : errorList){				
					content = content + errorXML + " <br>";				
				}
			}
			content = content + reportResult.getStatusmessage() + " <br>";	
		}
		
//		if(errors && reportResult.getValidationerrors() != null && reportResult.getValidationerrors().length > 0){
//			for ( BaseResultDTO error : reportResult.getValidationerrors()){				
//				String detailStr =  error.getStatusmessage();
//				content = content + detailStr + " <br>";				
//			}	
//		}else{
//			content = content + reportResult.getStatusmessage() + " <br>";	
//		}
//			
//		//Agregregar errores de Validacion de XML
//		if(errorList.size() > 0 ){			
//			for ( String errorXML : errorList){				
//				content = content + errorXML + " <br>";				
//			}			
//		}
		
		content = content + "<br><br> Atte.B2B Paris.";
		if(errors){
			sendMailToBBR("Proveedor:"+vendor.getName()+"("+vendor.getCode()+")<br>"+content);
		}
		return content;
	}
	
	private void sendMailToBBR(String content){		
		String subject = "B2B Paris: Actualización Capacidad Disponible VeV";
		String body = "";			
		try {
			String[] mailreciever = RegionalLogisticConstants.getInstance().getDEVELOPER_MAIL_ERROR();
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			body = body + content;
			Mailer.getInstance().sendMailBySession(mailreciever, null, null, mailsender, subject, body, true, null, mailSession);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se envio el mail del fracaso de envio de email");
		}		
	}
	
	private void sendMailToVendor(String content, VendorW vendor){		
		String subject = "B2B Paris: Actualización Capacidad Disponible VeV";
		String body = "Estimado Proveedor "+vendor.getName()+":  <br>";			
		try {
			String[] mailreciever = vendor.getEmail().split(",");
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			body = body + content;
			Mailer.getInstance().sendMailBySession(mailreciever, null, null, mailsender, subject, body, true, null, mailSession);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No se envio el mail del fracaso de envio de email");
		}		
	}
	
	@Resource
	private ManagedExecutorService executor;

	private void doBackUpMessage(String content, String number, String msgType){
		
		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try{
			executor.submit(new BackUpUtils(content, number, msgType));			
		}catch (RejectedExecutionException e) {
			e.printStackTrace();
		}			
	}
}
