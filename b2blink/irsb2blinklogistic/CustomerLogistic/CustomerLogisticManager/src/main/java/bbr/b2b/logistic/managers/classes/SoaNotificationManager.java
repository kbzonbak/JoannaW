package bbr.b2b.logistic.managers.classes;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import bbr.b2b.b2blink.logistic.xml.NotificacionOrden.NotificacionOrden;
import bbr.b2b.b2blink.logistic.xml.NotificacionOrden.ObjectFactory;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.OrderServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.OrderStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.OrderW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.managers.interfaces.SOANotificationManagerLocal;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;

@Stateless(name = "managers/SoaNotificationManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SoaNotificationManager implements SOANotificationManagerLocal{

	private static Logger logger = Logger.getLogger(SoaNotificationManager.class);
	private static Logger logger_soa = Logger.getLogger("SOALog");	
	
	@EJB
	SoaStateTypeServerLocal soastatetypeserver;
	
	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	@EJB
	SoaStateServerLocal soaStateServerLocal;
	
	@EJB
	BuyerServerLocal buyerserver;
	
	
	@EJB
	SchedulerManagerLocal schedulermanager;	
	
	private static Map<Long, BuyerW> buyerMap = null;	
	private static Map<Long, VendorW> vendorMap = null;	
	private static Map<Long, SoaStateW> ostMap = null;
	private static Map<String, SoaStateTypeW> soastMap = null;
	private static Map<String, OrderStateTypeW> orderstateMap = null;
	
	
	private static JAXBContext jc = null;
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionOrden");
		return jc;
	}	
	
	@Resource
	private SessionContext ctx;
	
	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
	
	public synchronized void doProcess() throws ServiceException{
		
		String result = "";
		Date now = new Date();
		
		if (buyerMap  == null){
			buyerMap = new HashMap<Long, BuyerW>();
		}
		if (vendorMap  == null){
			vendorMap = new HashMap<Long, VendorW>();
		}
		if (ostMap  == null){
			ostMap = new HashMap<Long, SoaStateW>();
		}		
		
		try {			
//			boolean sendNotif = Boolean.parseBoolean(B2BSystemPropertiesUtil.getProperty("sendNotif").trim());
//			int countNotif = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("countNotif").trim());
			boolean sendNotif = true;
			int countNotif = 300;
				
			if(!sendNotif) 
				return;					
		
			if (soastMap == null){
				soastMap = new HashMap<String, SoaStateTypeW>();
				try{
					SoaStateTypeW[] soaStArr = soastatetypeserver.getAllAsArray();
					for (SoaStateTypeW soaSt : soaStArr){
						if (!soastMap.containsKey(soaSt.getCode())){
							soastMap.put(soaSt.getCode(), soaSt);
						}
					}	
				}catch (ServiceException e) {
					e.printStackTrace();
				}	
			}
			if (orderstateMap == null){
				orderstateMap = new HashMap<String, OrderStateTypeW>();
				try{
					OrderStateTypeW[] soaStArr = orderstatetypeserver.getAllAsArray();
					for (OrderStateTypeW soaSt : soaStArr){
						if (!orderstateMap.containsKey(soaSt.getCode())){
							orderstateMap.put(soaSt.getCode(), soaSt);
						}
					}	
				}catch (ServiceException e) {
					e.printStackTrace();
				}	
			}
						
			logger.info("Evento automático de notificaciones a SOA iniciado");
						
			SoaStateTypeW notSt = soastMap.get("NOTIFICADO");
			
			StringBuilder sb = new StringBuilder("select order From Order as order " +//
					" where order.orderstatetype.code='LIBERADA' " +//
					" and order.soastatetype.code='POR_NOTIFICAR' " +//
					" order by order.created asc");
			
			List<OrderW> orderList= orderserver.getByQuery(1, countNotif, sb.toString());
			OrderW[] orderArr = orderList.toArray(new OrderW[orderList.size()]);
			
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
				VendorW vendorW = vendorMap.get(order.getVendorid());					
				
				// DATOS DE COMPRADOR
				if (!buyerMap.containsKey(order.getBuyerid())){
					BuyerW buyer = buyerserver.getById(order.getBuyerid());
					buyerMap.put(buyer.getId(), buyer);
				}
				BuyerW buyerW = buyerMap.get(order.getBuyerid());
				
				SoaStateTypeW orderTypeSoa = soastMap.get(order.getSoastatetypeid());
				//String report =	orderTypeSoa.getName() ;		
								
				// NOMBRE DEL SITIO
				String nombreportal = buyerW.getSitename();
				
				// NUMERO DE LA OC
				String ordernumber = order.getNumber().toString();				
				
				JAXBContext jc = getJC();
				ObjectFactory objFactory = new ObjectFactory();
				NotificacionOrden notification = objFactory.createNotificacionOrden();
				notification.setCodproveedor(vendorW.getRut());
				notification.setCodcomprador(buyerW.getRut());
				notification.setEstado("POR_NOTIFICAR");
				notification.setNombreportal("CUSTOMER_LOGISTICA");
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
					schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl" , "NotificadoOcSoa", String.valueOf(order.getNumber()), result);
					
					// CAMBIA ESTADO SOA DE OC A NOTIFICADA
					order.setSoastatetypeid(notSt.getId());
					
					updateSOAState(order, now);				
				} catch (Exception e) {
					e.printStackTrace();
				}	
			
			}	//End for
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento automático de notificaciones a SOA falló");
		}
		
		logger.info("Evento automático de notificaciones a SOA finalizado");
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void updateSOAState(OrderW orderW, Date now){
		
		try{
			orderW = orderserver.updateOrder(orderW);
			SoaStateW soaStateW = new SoaStateW();
			soaStateW.setWhen(now);
			soaStateW.setOrderid(orderW.getId());
			soaStateW.setSoastatetypeid(orderW.getSoastatetypeid());
			soaStateServerLocal.addSoaState(soaStateW);
			
		}catch (ServiceException e) {
			e.printStackTrace();
			logger.info("La OC " + orderW.getNumber() + " no se pudo actualizar");			
		}		
	}
	

}
