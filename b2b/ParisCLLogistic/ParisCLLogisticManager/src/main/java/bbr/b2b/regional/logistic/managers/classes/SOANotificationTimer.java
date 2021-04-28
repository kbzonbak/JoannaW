package bbr.b2b.regional.logistic.managers.classes;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.NotificacionOrden.NotificacionOrden;
import bbr.b2b.b2blink.logistic.xml.NotificacionOrden.ObjectFactory;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.managers.interfaces.SOANotificationTimerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.regional.logistic.soa.classes.DirectOrderSOAStateServerLocal;
import bbr.b2b.regional.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.regional.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.regional.logistic.soa.data.classes.DirectOrderSOAStateW;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;


@Stateless(name = "managers/SOANotificationTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SOANotificationTimer implements SOANotificationTimerLocal{

	private static Logger logger = Logger.getLogger(SOANotificationTimer.class);
	private static Logger logger_soa = Logger.getLogger("SOALog");
	
	private static boolean isServiceStarted = false;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;
	
	@EJB
	SOAStateServerLocal soastateserver;
	
	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;
	
	
	@EJB
	DirectOrderSOAStateServerLocal directordersoastateserver;
	
	@EJB
	DirectOrderServerLocal directorderserver;
	
	@EJB
	DirectOrderStateTypeServerLocal directorderstatetypeserver;
	
	@EJB
	SchedulerManagerLocal schedulermanager;	
		
	private static Map<Long, VendorW> vendorMap = null;	
	private static Map<Long, OrderStateTypeW> ostMap = null;
	private static Map<Long, DirectOrderStateTypeW> dostMap = null;
	private static Map<String, SOAStateTypeW> soastMap = null;
	
	private static JAXBContext jc = null;
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionOrden");
		return jc;
	}	
	
	@Resource
	private SessionContext ctx;
	
	public synchronized void scheduleTimer(long initialinterval, long milliseconds) {
		// Cancelar todos los timers previamente asociados a este manager
		TimerService ts = ctx.getTimerService();
		// Obtiene todos los timers asociados al bean
		Collection<Timer> timers = ts.getTimers();
		// ... y los cancela
		for (Iterator iterator = timers.iterator(); iterator.hasNext();) {
			Timer timer = (Timer) iterator.next();
			timer.cancel();
		}
		// Crear el timerservice
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron envío de notificaciones a SOA");
		
		vendorMap = new HashMap<Long, VendorW>();		
		ostMap = new HashMap<Long, OrderStateTypeW>();	
		dostMap = new HashMap<Long, DirectOrderStateTypeW>();	
		isServiceStarted = true;
	}
	
	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
	
	@Timeout
	public synchronized void timeoutHandler(Timer timer) {
		
		String result = "";
		
		if (vendorMap  == null){
			vendorMap = new HashMap<Long, VendorW>();
		}
		if (ostMap  == null){
			ostMap = new HashMap<Long, OrderStateTypeW>();
		}	
		
		try {			
			boolean sendNotif = Boolean.parseBoolean(B2BSystemPropertiesUtil.getProperty("sendNotif").trim());
			int countNotif = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("countNotif").trim());
			
			if (!isServiceStarted || !sendNotif) 
				return;					
		
			if (soastMap == null){
				soastMap = new HashMap<String, SOAStateTypeW>();
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
						
			logger.info("Evento autom�tico de notificaciones a SOA iniciado");
			
			Date now = new Date();			
			
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
				i++;
				if (i > countNotif)
					break;
				
				// DATOS DE PROVEEDOR
				if (!vendorMap.containsKey(order.getVendorid())){
					VendorW vendor = vendorserver.getById(order.getVendorid());
					vendorMap.put(vendor.getId(), vendor);
				}				
				
				String vendorCode = vendorMap.get(order.getVendorid()).getCode();					
				
				// DATOS DE COMPRADOR
				String buyerRut = B2BSystemPropertiesUtil.getProperty("BuyerRut");		
				
				// DATOS DE ESTADO DE LA OC
				if (!ostMap.containsKey(order.getCurrentstatetypeid())){
					OrderStateTypeW ost = orderstatetypeserver.getById(order.getCurrentstatetypeid());
					ostMap.put(ost.getId(), ost);
				}				
				
				String report = ostMap.get(order.getCurrentstatetypeid()).getName();		
								
				// NOMBRE DEL SITIO
				String nombreportal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
				
				// NUMERO DE LA OC
				String ordernumber = order.getNumber().toString();				
				
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
					schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/q_esbgrlcenco" , "NotificadoOcSoa", String.valueOf(order.getNumber()), result);
					
					// CAMBIA ESTADO SOA DE OC A NOTIFICADA
					order.setCurrentsoastatetypedate(now);
					order.setCurrentsoastatetypeid(notSt.getId());
					
					updateSOAState(order);				
				} catch (Exception e) {
					e.printStackTrace();
				}	
			
			}	//End for
			
			//Envio de Ordenes Directas..
			sendPendingDirectOrders(countNotif);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento autom�tico de notificaciones a SOA fall�");
		}
		
		logger.info("Evento autom�tico de notificaciones a SOA finalizado");
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void updateSOAState(OrderW order){
		
		try{
			order = orderserver.updateOrder(order);
			
			SOAStateW state = new SOAStateW();
			state.setOrderid(order.getId());
			state.setSoastatetypeid(order.getCurrentsoastatetypeid());
			state.setWhen(order.getCurrentsoastatetypedate());
			state = soastateserver.addSOAState(state);		
			
		}catch (ServiceException e) {
			e.printStackTrace();
			logger.info("La OC " + order.getNumber() + " no se pudo actualizar");			
		}		
	}
	
	//ENVIAR ORDENES DIRECTAS PENDIENTES
	private void sendPendingDirectOrders(int countNotif) throws OperationFailedException, NotFoundException, JAXBException{		
	
		String result = "";
		
		Date now = new Date();
		
		SOAStateTypeW porNotSt = soastMap.get("POR_NOTIFICAR");
		SOAStateTypeW notSt = soastMap.get("NOTIFICADO");
		
		// CONSULTA LAS OC QUE ESTEN ESTADO POR NOTIFICAR
		OrderCriteriaDTO ordercriteria = new OrderCriteriaDTO();
		ordercriteria.setAscending(true);
		ordercriteria.setPropertyname("currentsoastatetypedate");
		
		DirectOrderW[] directorderArr = directorderserver.getByPropertyAsArrayOrdered(1, countNotif, "currentsoastatetype.id", porNotSt.getId(), ordercriteria);

		// GENERA MENSAJE DE NOTIFICACION Y LO ENVIA
		int i = 0;
		for (DirectOrderW directorder : directorderArr){
			i++;
			if (i > countNotif)
				break;
			
			// DATOS DE PROVEEDOR
			if (!vendorMap.containsKey(directorder.getVendorid())){
				VendorW vendor = vendorserver.getById(directorder.getVendorid());
				vendorMap.put(vendor.getId(), vendor);
			}				
			
			String vendorCode = vendorMap.get(directorder.getVendorid()).getCode();					
			
			// DATOS DE COMPRADOR
			String buyerRut = B2BSystemPropertiesUtil.getProperty("BuyerRut");		
			
			// DATOS DE ESTADO DE LA OC
			if (!dostMap.containsKey(directorder.getCurrentstatetypeid())){
				DirectOrderStateTypeW ost = directorderstatetypeserver.getById(directorder.getCurrentstatetypeid());
				dostMap.put(ost.getId(), ost);
			}				
			
			String report = dostMap.get(directorder.getCurrentstatetypeid()).getName();		
							
			// NOMBRE DEL SITIO
			String nombreportal = B2BSystemPropertiesUtil.getProperty("NombrePortal");
			
			// NUMERO DE LA OC
			String ordernumber = directorder.getNumber().toString();				
			
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
			
			logger_soa.info("(" + i + ")Notificación de carga de OC Directa a SOA: " + ordernumber);
						
			// ENVIA EL MENSAJE A LA COLA
			try {
				schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/q_esbgrlcenco" , "NotificadoOcDirectaSoa", String.valueOf(directorder.getNumber()), result);
								
				// CAMBIA ESTADO SOA DE OC A NOTIFICADA
				directorder.setCurrentsoastatetypedate(now);
				directorder.setCurrentsoastatetypeid(notSt.getId());				
				
				updateDirectOrderSOAState(directorder);				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void updateDirectOrderSOAState(DirectOrderW directorder){
		
		try{
			directorder = directorderserver.updateDirectOrder(directorder);
			
			DirectOrderSOAStateW state = new DirectOrderSOAStateW();
			state.setOrderid(directorder.getId());
			state.setSoastatetypeid(directorder.getCurrentsoastatetypeid());
			state.setWhen(directorder.getCurrentsoastatetypedate());
			state = directordersoastateserver.addSOAState(state);
			
		}catch (ServiceException e) {
			e.printStackTrace();
			logger.info("La OC Directa " + directorder.getNumber() + " no se pudo actualizar : SOA");			
		}		
	}	
}
