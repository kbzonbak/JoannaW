package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes.Proveedores.Proveedor;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.report.classes.PendingSOAOrderDTO;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.regional.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;


@Stateless(name = "handlers/XmlToPendingSoaVendors")
public class XmlToPendingSoaVendors implements XmlToPendingSoaVendorsLocal {

	private static JAXBContext jc = null;

	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes");
		return jc;
	}

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	OrderServerLocal orderserver = null;
	
	@EJB
	DirectOrderServerLocal directorderserver = null;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;
	
	@EJB
	SchedulerManagerLocal schedulermanager;
	
	private void doValidateAckOcSoaMessage(SolicitudProveedoresOrdenesPendientes message) throws LoadDataException, OperationFailedException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		// CODIGO COMPRADOR
		if (message.getCodcomprador() == null || message.getCodcomprador().trim().equals("")){
			throw new LoadDataException("No hay datos para Codigo Comprador");
		}
		
		// CODIGOS PROVEEDORES
		if (message.getProveedores() == null || 
			message.getProveedores().getProveedor() == null || 
			message.getProveedores().getProveedor().size() == 0){
			throw new LoadDataException("No hay datos para Codigos de Proveedor");
		}		
		
		// CODIGO PROVEEDOR
		for (int i = 0; i < message.getProveedores().getProveedor().size(); i++){
			if (message.getProveedores().getProveedor().get(i) == null ||
				message.getProveedores().getProveedor().get(i).getCodigo().equals("")){
				throw new LoadDataException("No hay datos para Codigo de Proveedor");
			}			
			
			// VALIDA QUE LA FECHA TENGA FORMATO v??lido
			try {
				sdf.parse(message.getProveedores().getProveedor().get(i).getFechaActivacion());
			} catch (ParseException e) {
				throw new LoadDataException("El formato de la fecha de activaci??n no es v??lido, yyyy-MM-dd hh:mm:ss");
			}			
		}			
	}
	
	public void processMessage(SolicitudProveedoresOrdenesPendientes message) throws ServiceException {
		ArrayList<String> specialVendors=new ArrayList<String>(Arrays.asList(B2BSystemPropertiesUtil.getProperty("sspecialvendors").split(",")));
		Map<Long, VendorW> vendorIds = new HashMap<Long, VendorW>();
		Map<Long, Date> vendorDate = new HashMap<Long, Date>();
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		String nombrePortal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
		int soapendingreceptiontime = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("soapendingreceptiontime"));
		
		SOAStateTypeW recOKSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_OK");
				
		// VALIDACION DEL MENSAJE
		doValidateAckOcSoaMessage(message);
		
		// PROCESAMIENTO DEL MENSAJE 
		// COMPRADOR
		String codComprador = message.getCodcomprador();
						
		// PROVEEDOR
		List<Proveedor> provList = message.getProveedores().getProveedor();
				
		for (Proveedor prov : provList){
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", prov.getCodigo()); 
			
			if (vendorArr == null || vendorArr.length == 0){
				logger.info("No existe proveedor con c??digo " + prov.getCodigo());
				continue;
			}
			
			if (!vendorIds.containsKey(vendorArr[0].getId())){
				vendorIds.put(vendorArr[0].getId(), vendorArr[0]);
				
				try {
					vendorDate.put(vendorArr[0].getId(), sdf.parse(prov.getFechaActivacion()));
				} catch (ParseException e) {					
					e.printStackTrace();
				}				
			}			
		}
		
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		
		// RESTAMOS LOS X MINUTOS
		calendar.add(Calendar.MINUTE, (soapendingreceptiontime * -1));
		
		ObjectFactory objFactory = new ObjectFactory();
		ListaProveedoresOrdenesPendientes qlistaPendProv = objFactory.createListaProveedoresOrdenesPendientes();
		
		bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores proveedores = objFactory.createListaProveedoresOrdenesPendientesProveedores();
		
		for (Long vendorid : vendorIds.keySet()){
			bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores.Proveedor proveedor = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedor();
			
			proveedor.setCodigo(vendorIds.get(vendorid).getCode());
			proveedor.setNombre(vendorIds.get(vendorid).getTradename());
			
			// CONSULTA POR CANTIDAD DE OC PENDIENTES DE RECEPCIONAR DESDE SOA
			// tipo 1= normal, tipo 2 = solo info de cliente
			//Long pendingorders;
			Long pendingorders;
			Long pendingdirectorders;
			PendingSOAOrderDTO[] pendingoc;
			PendingSOAOrderDTO[] pendingdoc;
			
			if(!specialVendors.contains(proveedor.getNombre())){
				pendingoc = orderserver.getPendingSOAOrdersByVendor(recOKSt.getId(), vendorid, calendar.getTime(), vendorDate.get(vendorid),1);
			}else{
				pendingoc = orderserver.getPendingSOAOrdersByVendor(recOKSt.getId(), vendorid, calendar.getTime(), vendorDate.get(vendorid),2);
			}		
			pendingdoc = directorderserver.getPendingSOAOrdersByVendor(recOKSt.getId(), vendorid, calendar.getTime(), vendorDate.get(vendorid));
			
			
			logPendingSOAOrder(pendingoc,"OC");
			logPendingSOAOrder(pendingdoc,"VeV PD");
			

			pendingorders = (long) pendingoc.length;
			pendingdirectorders = (long) pendingdoc.length;

			proveedor.setNumpendientes(pendingorders.intValue() + pendingdirectorders.intValue());
			pendingorders = (long) pendingoc.length;
			pendingdirectorders = (long) pendingdoc.length;

			// se setea el maximo de orders y directorders a mostrar en el detalle
			int maxpendingorders = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxpendingorders"));
			int maxorders = pendingoc.length < maxpendingorders ? pendingoc.length : maxpendingorders;
			int maxdirectorders = pendingdoc.length < maxpendingorders ? pendingdoc.length : maxpendingorders;

			// setear detalles de oc
			Detalles detalles = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedorDetalles();
			List<Detalle> detallesList = detalles.getDetalle();

			// recorre las orders pendientes
			for (int i = 0; i < maxorders; i++) {

				Detalle detalle = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedorDetallesDetalle();
				// transforma fecha en string
				Date date = Date.from( pendingoc[i].getCurrentsoastatetypedate().atZone( ZoneId.systemDefault()).toInstant()) ;
				String fecha = sdf.format(date);

				detalle.setUltimoCambioEstadoSOA(fecha);
				detalle.setNumeroOC(Long.toString(pendingoc[i].getOrdernumber()));
				detalle.setEstadoSoaOC(pendingoc[i].getCurrentsoastate());
				detalle.setEstadoOC(pendingoc[i].getCurrentstatetype());
				detalle.setTipoOC(pendingoc[i].getOrdertype());
				detallesList.add(detalle);
			}
			// recorre las directOrders pendientes
			for (int i = 0; i < maxdirectorders; i++) {

				Detalle detalle = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedorDetallesDetalle();
				// transforma fecha en string
				Date date = Date.from( pendingdoc[i].getCurrentsoastatetypedate().atZone( ZoneId.systemDefault()).toInstant()) ;
				String fecha = sdf.format(date);

				detalle.setUltimoCambioEstadoSOA(fecha);
				detalle.setNumeroOC(Long.toString(pendingdoc[i].getOrdernumber()));
				detalle.setEstadoSoaOC(pendingdoc[i].getCurrentsoastate());
				detalle.setEstadoOC(pendingdoc[i].getCurrentstatetype());
				detalle.setTipoOC(pendingdoc[i].getOrdertype());
				detallesList.add(detalle);
			}

			if(detallesList != null && detallesList.size() > 0)
				proveedor.setDetalles(detalles);
			
			
			proveedores.getProveedor().add(proveedor);	
			
		}
		
		qlistaPendProv.setNombreportal(nombrePortal);
		qlistaPendProv.setCodcomprador(codComprador);
		qlistaPendProv.setProveedores(proveedores);
		String msgcontent = "";
		try {
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qlistaPendProv, sw);
			msgcontent = sw.toString();
			
			schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/q_esbgrlcenco", "PendingOCSoaVendor", "VENDORS", msgcontent);
			
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new OperationFailedException("Error al construir mensaje", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		logger.info("Se envi?? lista de ordenes pendientes para proveedores" + msgcontent );		
	}		

	private void logPendingSOAOrder(PendingSOAOrderDTO[] pendingoc, String type){
		SimpleDateFormat sdfemitted = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < pendingoc.length; i++) {
			logger.info("OC ("+type+") a Revisar:" + pendingoc[i].getOrdernumber() + " - " + sdfemitted.format(Date.from( pendingoc[i].getEmitted().atZone( ZoneId.systemDefault()).toInstant()) ) +" - " + pendingoc[i].getVendor() + " - " + pendingoc[i].getCurrentsoastate());
		}
		
	}
	
}
