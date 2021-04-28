package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes.Proveedores.Proveedor;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateTypeW;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;
import bbr.b2b.logistic.report.classes.PendingSOAOrderDTO;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;


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
	DdcOrderServerLocal directorderserver = null;
	
	@EJB
	DvrOrderServerLocal dvrorderserver = null;
	
	@EJB
	OrderServerLocal orderserver = null;
	
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
			
			// VALIDA QUE LA FECHA TENGA FORMATO V�LIDO
			try {
				sdf.parse(message.getProveedores().getProveedor().get(i).getFechaActivacion());
			} catch (ParseException e) {
				throw new LoadDataException("El formato de la fecha de activaci�n no es v�lido, yyyy-MM-dd hh:mm:ss");
			}			
		}			
	}
	
	public void processMessage(SolicitudProveedoresOrdenesPendientes message) throws ServiceException {
		
		Map<Long, VendorW> vendorIds = new HashMap<Long, VendorW>();
		Map<Long, Date> vendorDate = new HashMap<Long, Date>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		String nombreportal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
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
				logger.info("No existe proveedor con c�digo " + prov.getCodigo());
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
			PendingSOAOrderDTO[] pendingdvroc = null;
			PendingSOAOrderDTO[] pendingddcoc = null;
			
			pendingdvroc = orderserver.getPendingSOAOrdersByVendor(recOKSt.getId(), vendorid, calendar.getTime(), vendorDate.get(vendorid));
			pendingddcoc = orderserver.getPendingSOADdcOrdersByVendor(recOKSt.getId(), vendorid, calendar.getTime(), vendorDate.get(vendorid));
	
			logPendingSOAOrder(pendingdvroc, "DVR");
			logPendingSOAOrder(pendingddcoc, "DDC");
			proveedor.setNumpendientes(pendingdvroc.length + pendingddcoc.length);	
			proveedores.getProveedor().add(proveedor);			
		}
		
		qlistaPendProv.setNombreportal(nombreportal);
		qlistaPendProv.setCodcomprador(codComprador);
		qlistaPendProv.setProveedores(proveedores);
		String msgcontent = "";
		LocalDateTime date = LocalDateTime.now();
		try {
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qlistaPendProv, sw);
			msgcontent = sw.toString();
			
			schedulermanager.doAddMessageQueue("qcf_soa" ,"q_esbgrl" , "PendingOCSoaVendor", "VENDORS","", msgcontent, date);
			
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new OperationFailedException("Error al construir mensaje", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		logger.info("Se envi� lista de ordenes pendientes para proveedores" + msgcontent );		
	}		

	private void logPendingSOAOrder(PendingSOAOrderDTO[] pendingoc, String type){
		for (int i = 0; i < pendingoc.length; i++) {
			logger.info("OC ("+type+") a Revisar:" + pendingoc[i].getOrdernumber() + " - " + pendingoc[i].getEmitted() +" - " + pendingoc[i].getVendor() + " - " + pendingoc[i].getCurrentsoastate());
		}
		
	}


}

