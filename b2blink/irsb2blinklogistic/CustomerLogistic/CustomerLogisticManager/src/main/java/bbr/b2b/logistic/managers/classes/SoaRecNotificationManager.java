package bbr.b2b.logistic.managers.classes;


import java.io.StringWriter;
import java.util.Date;
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

import bbr.b2b.b2blink.logistic.xml.NotificacionRecOC.NotificacionRecOC;
import bbr.b2b.b2blink.logistic.xml.NotificacionRecOC.ObjectFactory;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.ReceptionServerLocal;
import bbr.b2b.logistic.customer.classes.SoaRecStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.data.wrappers.SoaRecStateW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.managers.interfaces.SoaRecNotificationManagerLocal;


@Stateless(name = "managers/SoaRecNotificationManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SoaRecNotificationManager implements SoaRecNotificationManagerLocal{

	private static Logger logger = Logger.getLogger(SoaRecNotificationManager.class);
	private static Logger logger_soa = Logger.getLogger("SOALog");
	
	private static boolean isServiceStarted = false;
	
	@EJB
	SoaStateTypeServerLocal soastatetypeserver;
	
	@EJB
	SoaRecStateServerLocal soarecstateserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	BuyerServerLocal buyerserver;
	
	@EJB
	ReceptionServerLocal receptionserver;
	
	@EJB
	SchedulerManagerLocal schedulermanager;	
	
	private static Map<Long, VendorW> vendorMap = null;	
	private static Map<String, SoaStateTypeW> soastMap = null;
	
	private static JAXBContext jc = null;
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionRecOC");
		return jc;
	}	
	
	@Resource
	private SessionContext ctx;
	
	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
	
	public synchronized void doProcess() throws ServiceException{
			
		try {			
			boolean sendNotif = true;
			int countNotif = 300;
			
			if (!sendNotif) 
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
						
			logger.info("Evento automático de notificaciones a SOA iniciado (Recepciones)");
			
			Date now = new Date();			
			
			SoaStateTypeW porNotSt = soastMap.get("POR_NOTIFICAR");
			SoaStateTypeW notSt = soastMap.get("NOTIFICADO");			
			
			// CONSULTA LAS OC QUE ESTEN ESTADO POR NOTIFICAR
			OrderCriteriaDTO ordercriteria = new OrderCriteriaDTO();
			ordercriteria.setAscending(true);
			ordercriteria.setPropertyname("soastatetype");
			
			ReceptionW[] RecArr = receptionserver.getByPropertyAsArrayOrdered(1, countNotif, "soastatetype.id", porNotSt.getId(), ordercriteria);			
			// GENERA MENSAJE DE NOTIFICACION Y LO ENVIA
			vendorMap = new HashMap<Long, VendorW>();
			int i = 0;
			for (ReceptionW rec : RecArr){
				i++;
				if (i > countNotif)
					break;
				
				// DATOS DE PROVEEDOR
				if (!vendorMap.containsKey(rec.getVendorid())){
					VendorW vendor = vendorserver.getById(rec.getVendorid());
					vendorMap.put(vendor.getId(), vendor);
				}				
				
				String vendorCode = vendorMap.get(rec.getVendorid()).getCode();					
				
				// DATOS DE COMPRADOR			
				Long buyerid = rec.getBuyerid();
				BuyerW buyer = buyerserver.getById(buyerid);
				
				// NUMERO DE LA REC OC
				String receptionnumber = String.valueOf(rec.getReceptionnumber());				
				
				JAXBContext jc = getJC();
				ObjectFactory objFactory = new ObjectFactory();
				NotificacionRecOC notification = objFactory.createNotificacionRecOC();
				notification.setCodproveedor(vendorCode);
				notification.setCodcomprador(buyer.getRut());				
				notification.setNombreportal("CUSTOMER_LOGISTICA");//TODO probar
				notification.setNumrecepcion(receptionnumber);
				
				// Obtiene string XML para enviarlo a la cola
				Marshaller m = jc.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(notification, sw);
				String result = sw.toString();
				
				logger_soa.info("("+i+")Notificación de carga de Recepción de OC a SOA: "+receptionnumber);
				logger_soa.info(result);				
				
				
				// ENVIA EL MENSAJE A LA COLA
				try {
					schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl" , "NotificadoRecSoa", String.valueOf(rec.getReceptionnumber()), result);
					
					// CAMBIA ESTADO SOA DE REC OC A NOTIFICADA
					rec.setSoastatetypeid(notSt.getId());
					
					updateSOAState(rec);				
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento automático de notificaciones a SOA falló (Recepciones)" );
		}
		logger.info("Evento automático de notificaciones a SOA finalizado (Recepciones)");
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void updateSOAState(ReceptionW rec){
		
		try{
			Date now = new Date();
			rec = receptionserver.updateReception(rec);
			
			SoaRecStateW state = new SoaRecStateW();
			state.setReceptionid(rec.getId());
			state.setSoastatetypeid(rec.getSoastatetypeid());
			state.setWhen(now);
			state = soarecstateserver.addSoaRecState(state);		
			
		}catch (ServiceException e) {
			e.printStackTrace();
			logger.info("La Recepción de OC " + rec.getReceptionnumber() + " no se pudo actualizar");			
		}		
	}	
}

