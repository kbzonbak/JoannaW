package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Bultos.Bulto;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Bultos.Bulto.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.DocsTributario;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.DocsTributario.DocTributario;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.report.classes.UploadPackingListInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.UploadPackingListResultDTO;
import bbr.b2b.logistic.report.classes.UserLogDataDTO;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.storage.managers.interfaces.FileServiceManagerLocal;
import bbr.b2b.logistic.utils.BackUpUtils;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;
import bbr.common.dataset.util.CSVConverter;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataColumnStyle;
import bbr.common.dataset.util.DataColumnStyleInfo;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;


@Stateless(name = "handlers/XmlToPLProveedor")
public class XmlToPLProveedor implements XmlToPLProveedorLocal {

	private static Logger logger = Logger.getLogger("SOALog");
	private static JAXBContext jc = null;

	@Resource
	ManagedExecutorService executor;
	
	@EJB
	VendorServerLocal vendorserver = null;
	
	@EJB
	DvrDeliveryServerLocal deliveryServerLocal = null;
	
	@EJB
	DeliveryReportManagerLocal deliveryReportManagerLocal;
	
	@EJB
	FileServiceManagerLocal fileservicemanager;
	
	@EJB
	private SchedulerManagerLocal schedulermanager;
	
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RespuestaTicket");
		return jc;
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processMessage(PLProveedor message, Long ticketNumber) throws LoadDataException, OperationFailedException{
		
		String error = "";
		List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
		UploadPackingListResultDTO uploadPackingListResultDTO = null;
		
		//validaciones generales
		
		if(message.getNroCita() == null || message.getNroCita().equals("")){
			error = "Header: El nro de cita es obligatorio ";
			baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));
		}
		
		if(message.getCodProveedor() == null || message.getCodProveedor().equals("")){
			error = "Header: El Código de proveedor es obligatorio ";
			baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));
		}
		
		DvrDeliveryW delivery = null;
		List<DvrDeliveryW> listDeliveryW = new ArrayList<DvrDeliveryW>();
		try {
			listDeliveryW =	deliveryServerLocal.getByProperty("number", Long.valueOf(message.getNroCita()));
		} catch (NotFoundException | NumberFormatException e1) {
			error = "Cita " + message.getNroCita() + " Error de formato";
			baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));
		}
		
		if(listDeliveryW.size() == 0){
			error = "Cita " + message.getNroCita() + " no existe";
			baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));
		} else {
			delivery = listDeliveryW.get(0); 
		}
		
		if(baseresulList.size() > 0){
			uploadPackingListResultDTO = new UploadPackingListResultDTO();
			uploadPackingListResultDTO.setStatuscode("-1");
			uploadPackingListResultDTO.setStatusmessage("Existe uno o mas registros con datos obligatorios nulos o vacíos");
			uploadPackingListResultDTO.setValidationerrors((BaseResultDTO[]) baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
			logger.info("La CITA "+ message.getNroCita() +" no se ha recepcionado");
			String cita = "";
			if(!(message.getNroCita() == null) && !message.getNroCita().isEmpty()){
				cita = message.getNroCita();
			}
			sendTicketResponse(cita, message.getVendedor().getCodigo(), ticketNumber, System.getProperty("SenderIdentification"), uploadPackingListResultDTO);
			return;
		}
		
		VendorW vendor = new VendorW();
		
		try {
			vendor = vendorserver.getById(delivery.getVendorid());
		} catch (NotFoundException e1) {
			throw new OperationFailedException("No existe el vendor con id: "+ delivery.getVendorid());
		}
		
		if(!vendor.getCode().equals(message.getCodProveedor())){
			error = "Cita " + message.getNroCita() + " proveedor no corresponde al informado";
			baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));
		}
		
		
		// construccion del excel
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
		String realfilename = "PPL_" + dateFormat.format(new Date()) + " hrs.csv";
		DataRow row = null;
		
		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable("Detalle Packing List");
		
		
		DataColumn col01 = new DataColumn("rownumber", Integer.class);
		DataColumn col02 = new DataColumn("ordernumber", Long.class);
		DataColumn col03 = new DataColumn("locationcode", String.class);
		DataColumn col04 = new DataColumn("lpnnumber",String.class);
		DataColumn col05 = new DataColumn("itemcode", String.class);
		DataColumn col06 = new DataColumn("units", Double.class);
		DataColumn col07 = new DataColumn("documentnumber", String.class);
		DataColumn col08 = new DataColumn("documenttype", String.class);
		
		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);
		dt0.setShowcolumnheaders(false);
		int rownumber = 0;
		
		for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
			Bulto bulto = message.getBultos().getBulto().get(i);
			for(Detalle detalle : bulto.getDetalles().getDetalle()){
				rownumber+=1;
				row = dt0.newRow();
				row.setCellValue("rownumber", rownumber);
				
				if (detalle.getNoc() != null && !detalle.getNoc().isEmpty()) {
					row.setCellValue("ordernumber", Long.parseLong(detalle.getNoc()));
				} else {
					error = "Fila " + (rownumber) + ": El número de orden es obligatorio";
					baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));					
				}
				
				if (bulto.getCodLocalDestino() != null && !bulto.getCodLocalDestino().isEmpty()) {
					row.setCellValue("locationcode", bulto.getCodLocalDestino());
				} else {
					error = "Fila " + (rownumber) + ": El código de local de destino es obligatorio";
					baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));					
				}
				
				if (bulto.getNroBulto() != null && !bulto.getNroBulto().isEmpty()) {
					row.setCellValue("lpnnumber", bulto.getNroBulto());
				} else {
					error = "Fila " + (rownumber) + ": El número de bulto es obligatorio";
					baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));					
				}
				
				if (detalle.getCodProdCliente() != null && !detalle.getCodProdCliente().isEmpty()) {
					row.setCellValue("itemcode", detalle.getCodProdCliente());
				} else {
					error = "Fila " + (rownumber) + ": El código de producto es obligatorio";
					baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));					
				}
				
				if (detalle.getCantidadUnidades() != null && !detalle.getCantidadUnidades().isEmpty()) {
					row.setCellValue("units", Double.valueOf(detalle.getCantidadUnidades()));
				} else {
					error = "Fila " + (rownumber) + ": La cantidad de unidades es obligatoria";
					baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));					
				}
				
				if (detalle.getNoDocTributario() != null && !detalle.getNoDocTributario().isEmpty()) {
					row.setCellValue("documentnumber", detalle.getNoDocTributario());
					//si existe el nro de documento se busca el tipo
					if ( !(getDocType(message.getDocsTributario(), detalle.getNoDocTributario())).equals("")) {
						String doctype = getDocType(message.getDocsTributario(), detalle.getNoDocTributario());
						if(doctype.isEmpty()){
							error = "Fila " + (rownumber) + ": El tipo de documento es obligatorio para el documento: " + detalle.getNoDocTributario();
							baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));
						}else{
							row.setCellValue("documenttype",doctype); 
						}
					} else {
						error = "Fila " + (rownumber) + ": No existe documento asociado a la orden: " + detalle.getNoc();
						baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));					
					}
					
				} else {
					error = "Fila " + (rownumber) + ": El número de documento es obligatorio";
					baseresulList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "-1", error, false));					
				}
				dt0.addRow(row);
			}
		}
		
		
		
		if(baseresulList.size() > 0){
			uploadPackingListResultDTO = new UploadPackingListResultDTO();
			uploadPackingListResultDTO.setStatuscode("-1");
			uploadPackingListResultDTO.setStatusmessage("Existe uno o mas registros con datos obligatorios nulos o vacíos");
			uploadPackingListResultDTO.setValidationerrors((BaseResultDTO[]) baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
			logger.info("La CITA "+ message.getNroCita() +" no se ha recepcionado");		
		} else {
			//Si no hay errores se genera el archivo csv 
			// ESTILOS
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(0);
			DataColumnStyle style1 = new DataColumnStyle("decimal", df, 20);

			DataColumnStyleInfo info = new DataColumnStyleInfo();
			info.addDataColumnStyles(style1, col06);

			// Crear Archivo
			// CSV
			CSVConverter converter = new CSVConverter();
			converter.setSeparator(',');
			converter.setStringSeparator('\'');
			converter.setUseStringSeparator(true);
			String finalPath = "";
			try {
				finalPath = System.getProperty("FILE_PACKING_LIST_PATH") + realfilename;
				converter.ExportToCSV(dt0, finalPath, Charset.forName("ISO-8859-1"));
				logger.info("Archivo " + finalPath + " se ha generado correctamente.");
			} catch (IOException e) {
				e.printStackTrace();
				throw new LoadDataException("Error al generar data para carga");
			}
			UploadPackingListInitParamDTO uploadPackingListInitParamDTO = new UploadPackingListInitParamDTO();
			uploadPackingListInitParamDTO.setDvrdeliveryid(delivery.getId());
			uploadPackingListInitParamDTO.setFilename(finalPath);
			uploadPackingListInitParamDTO.setVendorcode(vendor.getCode());
			
			UserLogDataDTO userlog = new UserLogDataDTO();
			userlog.setUsername("BBR");
			userlog.setUsertype("SOA");
			uploadPackingListResultDTO  = deliveryReportManagerLocal.doUploadPackingList(uploadPackingListInitParamDTO, userlog);
		}
		
		sendTicketResponse(message.getNroCita(), message.getVendedor().getCodigo(), ticketNumber, System.getProperty("SenderIdentification"), uploadPackingListResultDTO);
	}
	
	private String getDocType(DocsTributario docs, String docnumber) throws LoadDataException{
		
		String tipodoc = "";
		for (DocTributario doc : docs.getDocTributario()) {
			if(docnumber.equals(doc.getNoDocTributario())){
				return  doc.getTipoDocTributario();
			}
		}
		return tipodoc;
	}
	
	private void sendTicketResponse(String nroCita, String vendor, Long ticketNumber, String codigoportal, UploadPackingListResultDTO uploadPackingListResultDTO){
		//RESPUESTA HACIA SOA
		ObjectFactory objFactory = new ObjectFactory();
		RespuestaTicket qrespuestaticket = objFactory.createRespuestaTicket();
		qrespuestaticket.setTicketnumber(ticketNumber == null ? 0L : ticketNumber.longValue());
		qrespuestaticket.setCodproveedor(vendor);
		qrespuestaticket.setNombreportal(codigoportal);
		qrespuestaticket.setServicename("CPL");
		qrespuestaticket.setDescripcion(uploadPackingListResultDTO.getStatusmessage());
		
		//se completan detalles
		Detalles detallesticket = objFactory.createRespuestaTicketDetalles();
		List<bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle> detalleticketlist = detallesticket.getDetalle();
		BaseResultDTO[] resultserror = uploadPackingListResultDTO.getValidationerrors();

		//se agregan los resultados de WS
		if(resultserror != null){
			qrespuestaticket.setEstadoticket("-1");
			logger.info("El packingList CITA: "+ nroCita +" se ha recepcionada con errores");	
			for (int i = 0; i < resultserror.length; i++) {
				bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle detalleticket = objFactory.createRespuestaTicketDetallesDetalle();
				detalleticket.setTipo("linea");
				detalleticket.setCodigo(resultserror[i].getStatuscode());
				detalleticket.setEstado("");
				detalleticket.setDescripcion(resultserror[i].getStatusmessage());
				detalleticketlist.add(detalleticket);
			}
		}else{
			String status = !uploadPackingListResultDTO.getStatuscode().equals("0") ? "-1" : uploadPackingListResultDTO.getStatuscode();
			qrespuestaticket.setEstadoticket(status);
			if(status.equals("0")){
				logger.info("El packingList CITA: "+ nroCita +" se ha recepcionado con éxito");
			}else{
				logger.info("Error al recepcionar PL de CITA: "+ nroCita);	
			}
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
			
			System.out.println(result);

			// RESPALDA MENSAJE			
			doBackUpMessage(result, String.valueOf(qrespuestaticket.getTicketnumber()), "SOA");
			LocalDateTime now = LocalDateTime.now();
			try {			
				schedulermanager.doAddMessageQueue("qcf_soa", "q_esbgrl", "RespuestaTicket", String.valueOf(ticketNumber), "", result,now);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	private String createErrorFile(String nroCita, BaseResultDTO[] errores){
//		String bucketname = B2BSystemPropertiesUtil.getProperty("S3_BUCKET_NAME");
//		String folderPath = B2BSystemPropertiesUtil.getProperty("FILE_PACKING_LIST_PATH");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String[][] data = new String[errores.length][1];
//		for (int i = 0; i < errores.length; i++) {
//			data[i][0] = errores[i].getStatusmessage();
//		}
//		String filename = "PL_Error" + nroCita + "_" + sdf.format(new Date()) + ".xls";
//		try {
//			BBRStringUtils.doCSVString(data, "|", filename);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//			logger.error("No fue posible generar archivo de errores " + nroCita);
//		}
//		try {
//			File file = new File(folderPath + filename);
//			S3FileDataDTO filedata = fileservicemanager.createObject(bucketname, filename, file, filename);
//			// fileservicemanager.listObjectsInBucket(bucketname);
//			// se setea la url publica
//			return String.valueOf(filedata.getSignedUrl());
//		} catch (Exception e2) {
//			e2.printStackTrace();
//			logger.error("No fue posible subir archivo de errores al S3 " + nroCita);
//		}
//		return null;
//	}
	
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
