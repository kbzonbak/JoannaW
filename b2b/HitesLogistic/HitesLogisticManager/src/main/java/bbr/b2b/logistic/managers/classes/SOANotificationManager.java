package bbr.b2b.logistic.managers.classes;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateTypeServerLocal;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateTypeW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderW;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.managers.interfaces.SOANotificationManagerLocal;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.logistic.soa.xml.classes.NotificacionOrden;
import bbr.b2b.logistic.soa.xml.classes.ObjectFactory;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;


@Stateless(name = "managers/SOANotificationManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SOANotificationManager implements SOANotificationManagerLocal {

	private static Logger logger = Logger.getLogger(SOANotificationManager.class);	
	private static Logger logger_soa = Logger.getLogger("SOALog");


	@EJB
	SOAStateTypeServerLocal soastatetypeserver;
	
	@EJB
	SOAStateServerLocal soastateserver;
	
	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	DvrOrderStateTypeServerLocal dvrorderstatetypeserver;
	
	@EJB
	DdcOrderStateTypeServerLocal ddcorderstatetypeserver;
	
	@EJB
	SchedulerManagerLocal schedulermanager;	
	
	@EJB
	DvrOrderServerLocal dvrorderserver;
	
	@EJB
	DdcOrderServerLocal ddcorderserver;
	
	
	private static Map<Long, VendorW> vendorMap = new HashMap<>();		
	private static Map<Long, DvrOrderStateTypeW> dvrostMap  = new HashMap<>();	
	private static Map<Long, DdcOrderStateTypeW> ddcostMap = new HashMap<>();
	private static Map<String, SOAStateTypeW> soastMap = null;
	
	private static JAXBContext jc = null;
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null){
			jc = JAXBContext.newInstance("bbr.b2b.logistic.soa.xml.classes");
		}
		return jc;
	}	
	
	@Resource
	private SessionContext ctx;
	
	@TransactionTimeout(3600)
	public synchronized void doProcess() throws ServiceException {
		try{
			
			int countNotif = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("countNotif").trim());
			String result = "";			
			
			if (soastMap == null){
				soastMap = new HashMap<>();
				try{
					SOAStateTypeW[] soaStArr = soastatetypeserver.getAllAsArray();
					for (SOAStateTypeW soaSt : soaStArr){
						if (!soastMap.containsKey(soaSt.getCode())){
							soastMap.put(soaSt.getCode(), soaSt);
						}
					}	
				}catch (ServiceException e) {
					e.printStackTrace();
				}	
			}
			
			
			logger.info("Evento automático de notificaciones a SOA iniciado");
			
			LocalDateTime now = LocalDateTime.now();			
			
			SOAStateTypeW porNotSt = soastMap.get("POR_NOTIFICAR");
			SOAStateTypeW notSt = soastMap.get("NOTIFICADO");
			
			// CONSULTA LAS OC QUE ESTEN ESTADO POR NOTIFICAR
			OrderCriteriaDTO ordercriteria = new OrderCriteriaDTO();
			ordercriteria.setAscending(true);
			ordercriteria.setPropertyname("currentsoastatetypedate");
			
			OrderW[] orderArr = orderserver.getByPropertyAsArrayOrdered(1, countNotif, "currentsoastatetype.id", porNotSt.getId(), ordercriteria);
									
			// GENERA MENSAJE DE NOTIFICACION Y LO ENVIA
			int i = 0;
			for (OrderW order : orderArr){
				
				DvrOrderW dvrorder = new DvrOrderW();
				DdcOrderW ddcorder = new DdcOrderW();
				
				
				
				i++;
				if (i > countNotif)
					break;
				
				// DATOS DE PROVEEDOR
				if (!vendorMap.containsKey(order.getVendorid())){
					VendorW vendor = vendorserver.getById(order.getVendorid());
					vendorMap.put(vendor.getId(), vendor);
				}				
				
				String vendorCode = vendorMap.get(order.getVendorid()).getCode();				
				String report = "";
				
				// DATOS DE COMPRADOR
				String buyerRut = "0001";	
				boolean dvr = false;
				
				try {
					dvrorder = dvrorderserver.getById(order.getId());
					// DATOS DE ESTADO DE LA OC
					if (!dvrostMap.containsKey(dvrorder.getCurrentstatetypeid())){
						DvrOrderStateTypeW ost = dvrorderstatetypeserver.getById(dvrorder.getCurrentstatetypeid());
						dvrostMap.put(ost.getId(), ost);
					}	
					report = dvrostMap.get(dvrorder.getCurrentstatetypeid()).getDescription();
					dvr = true;
				} catch (Exception e) {
					ddcorder = ddcorderserver.getById(order.getId());
					// DATOS DE ESTADO DE LA OC
					if (!ddcostMap.containsKey(ddcorder.getCurrentstatetypeid())){
						DdcOrderStateTypeW ddcost = ddcorderstatetypeserver.getById(ddcorder.getCurrentstatetypeid());
						ddcostMap.put(ddcost.getId(), ddcost);
					}	
					report = ddcostMap.get(ddcorder.getCurrentstatetypeid()).getName();
				}
								
				// NOMBRE DEL SITIO
				String nombreportal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
				
				// NUMERO DE LA OC
				String ordernumber = String.valueOf(order.getNumber());				
				JAXBContext jc = getJC();
				ObjectFactory objFactory = new ObjectFactory();
				NotificacionOrden notification = objFactory.createNotificacionOrden();
				notification.setCodproveedor(vendorCode);
				notification.setCodcomprador(buyerRut);
				notification.setEstado(report);
				notification.setNombreportal(nombreportal);
				notification.setNumorden(ordernumber);
				
				// Obtiene string XML para enviarlo a la cola
				Marshaller m = jc.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(notification, sw);
				result = sw.toString();
				
				logger_soa.info("(" + i + ")Notificación de carga de OC a SOA: " + ordernumber);
								
				// ENVIA EL MENSAJE A LA COLA
				try {
					schedulermanager.doAddMessageQueue("qcf_soa","q_esbgrl" , "NotificadoOcSoa", String.valueOf(order.getNumber()),"", result, now);
					
					// CAMBIA ESTADO SOA DE OC A NOTIFICADA
					if(dvr){
						dvrorder.setCurrentsoastatetypedate(now);
						dvrorder.setCurrentsoastatetypeid(notSt.getId());
						dvrorderserver.updateDvrOrder(dvrorder);
					}else{
						ddcorder.setCurrentsoastatetypedate(now);
						ddcorder.setCurrentsoastatetypeid(notSt.getId());
						ddcorderserver.updateDdcOrder(ddcorder);
					}
					updateSOAState(order);				
				} catch (Exception e) {
					e.printStackTrace();
				}	
			
			}	//End for
			
			
		}catch (Exception e) {
			logger.error("DEMONIO_NOTIFICACION_SOA: Error excepcional!");
			getCtx().setRollbackOnly();
			e.printStackTrace();
		}	
	}

	public SessionContext getCtx() {
		return ctx;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void updateSOAState(OrderW order){
		
		try{
			
			SOAStateW state = new SOAStateW();
			state.setOrderid(order.getId());
			state.setSoastatetypeid(order.getCurrentsoastatetypeid());
			state.setWhen(order.getCurrentsoastatetypedate());
			soastateserver.addSOAState(state);		
			
		}catch (ServiceException e) {
			e.printStackTrace();
			logger.info("La OC " + order.getNumber() + " no se pudo actualizar");			
		}		
	}
	
}
