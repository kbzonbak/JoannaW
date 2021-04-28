package bbr.b2b.soa.integrator.manager.classes;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.commercial.xml.NotificacionInventario.NotificacionInventario;
import bbr.b2b.b2blink.commercial.xml.NotificacionVentas.NotificacionVentas;
import bbr.b2b.b2blink.logistic.xml.NotificacionOrden.NotificacionOrden;
import bbr.b2b.b2blink.logistic.xml.NotificacionOrden.ObjectFactory;
import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.entities.Order;
import bbr.b2b.soa.integrator.facade.entities.SellOut;
import bbr.b2b.soa.integrator.facade.entities.SellOutType;
import bbr.b2b.soa.integrator.facade.entities.SoaState;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SellOutRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SellOutTypeRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;

@Service
@Transactional
public class SendSOANotificationManager {
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private JMSMessageService messageService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;
	
	@Autowired
	private VendorRepository companyRepository;
	
	@Autowired
	private SoaStateRepository soaStateRepository;
	
	@Autowired
	private SellOutTypeRepository sellOutTypeRepository;
	
	@Autowired
	private SellOutRepository sellOutRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendSOANotificationManager.class);
	
	private static JAXBContext ocJc = null;
	private static JAXBContext salesJc = null;
	private static JAXBContext invJc = null;
	
	private static JAXBContext getOcJC() throws JAXBException {
		if (ocJc == null)
			ocJc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionOrden");
		return ocJc;
	}
	
	private static JAXBContext getSalesJC() throws JAXBException {
		if (salesJc == null)
			salesJc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.NotificacionVentas");
		return salesJc;
	}
	
	private static JAXBContext getInvJC() throws JAXBException {
		if (invJc == null)
			invJc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.NotificacionInventario");
		return invJc;
	}

	public void sendOcNotifications() {
		
		try {
			
			Optional<SoaStateType> toNotifyOp = soaStateTypeRepository.findByCode("POR_NOTIFICAR");
			Optional<SoaStateType> notifiedOp = soaStateTypeRepository.findByCode("NOTIFICADO");
			
			SoaStateType toNotify = null;
			SoaStateType notified = null;
			
			if (toNotifyOp.isPresent() && notifiedOp.isPresent()) {
			
				toNotify = toNotifyOp.get();
				notified = notifiedOp.get();
				
				// CONSULTA POR LAS ORDENES EN ESTADO POR NOTIFICAR Y COMPLETAS
				List<Order> orders = orderRepository.findByCurrentSoaStateTypeIdAndComplete(toNotify.getId(), true);
				
				if (orders != null && !orders.isEmpty()) {
					
					for (Order order : orders) {
						
						String orderNumber = order.getOcNumber().toString();
						
						Optional<Vendor> vendorOp = companyRepository.findById(order.getVendor().getId());
						Vendor vendor = null;	
						
						if (vendorOp.isPresent()) {
							vendor = vendorOp.get();
							
							JAXBContext jc = getOcJC();
							ObjectFactory objFactory = new ObjectFactory();
							NotificacionOrden notification = objFactory.createNotificacionOrden();
							notification.setCodproveedor(vendor.getCode());
							notification.setCodcomprador("");
							notification.setEstado(toNotify.getDescription());
							notification.setNombreportal(serviceConfiguration.getPortalName());
							notification.setNumorden(orderNumber);

							// Obtiene string XML para enviarlo a la cola
							Marshaller m = jc.createMarshaller();
							m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
							StringWriter sw = new StringWriter();
							m.marshal(notification, sw);
							String result = sw.toString();

							LOGGER.info("Notificación de carga de OC a SOA: " + orderNumber);

							// ENVIA EL MENSAJE A LA COLA							
							messageService.send(QueueDefinitions.Q_SOA_OUT, result);						

							// CAMBIA ESTADO SOA DE OC A NOTIFICADA
							order.setCurrentSoaStateType(notified);
							order.setCurrentSoaStateTypeDate(LocalDateTime.now());
							
							orderRepository.save(order);
							
							// SE DEJA EN ESTADO NOTIFICADA
							SoaState state = new SoaState();
							state.setOrder(order);
							state.setSoaStateType(notified);
							state.setWhen(LocalDateTime.now());
							state.setComment("Notificado");
							
							soaStateRepository.save(state);						
						}						
					}					
				}			
				else
					LOGGER.info("No hay ordenes para notificar");
			}		
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendSalesNotifications() {
	
		try {
			
			Optional<SoaStateType> toNotifyOp = soaStateTypeRepository.findByCode("POR_NOTIFICAR");
			Optional<SoaStateType> notifiedOp = soaStateTypeRepository.findByCode("NOTIFICADO");
			Optional<SellOutType> salesTypeOp = sellOutTypeRepository.findByCode("VTA");
						
			SoaStateType toNotify = null;
			SoaStateType notified = null;
			SellOutType salesType = null; 
			
			if (toNotifyOp.isPresent() && notifiedOp.isPresent() && salesTypeOp.isPresent()) {
			
				toNotify = toNotifyOp.get();
				notified = notifiedOp.get();
				salesType = salesTypeOp.get();
				
				// CONSULTA POR LAS VENTAS EN ESTADO POR NOTIFICAR
				List<SellOut> sellOuts = sellOutRepository.findBySoaStateTypeIdAndSellOutTypeId(toNotify.getId(), salesType.getId());
				
				if (sellOuts != null && !sellOuts.isEmpty()) {
					
					for (SellOut sellOut : sellOuts) {
						
						Optional<Vendor> vendorOp = companyRepository.findById(sellOut.getVendor().getId());
						Vendor vendor = null;	
						
						if (vendorOp.isPresent()) {
							vendor = vendorOp.get();
							
							JAXBContext jc = getSalesJC();
							bbr.b2b.b2blink.commercial.xml.NotificacionVentas.ObjectFactory objFactory = new bbr.b2b.b2blink.commercial.xml.NotificacionVentas.ObjectFactory();
							NotificacionVentas notification = objFactory.createNotificacionVentas();
							notification.setCodproveedor(vendor.getCode());							
							notification.setNombreportal(serviceConfiguration.getPortalName());						

							// Obtiene string XML para enviarlo a la cola
							Marshaller m = jc.createMarshaller();
							m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
							StringWriter sw = new StringWriter();
							m.marshal(notification, sw);
							String result = sw.toString();

							LOGGER.info("Notificación de carga de Ventas a SOA, Proveedor: " + vendor.getCode());

							// ENVIA EL MENSAJE A LA COLA							
							messageService.send(QueueDefinitions.Q_SOA_OUT, result);						

							// CAMBIA ESTADO SOA DE SELLOUT A NOTIFICADA
							sellOut.setSoaStateType(notified);
							
							sellOutRepository.save(sellOut);
							
							// SE DEJA EN ESTADO NOTIFICADA
							SoaState state = new SoaState();
							state.setSellOut(sellOut);
							state.setSoaStateType(notified);
							state.setWhen(LocalDateTime.now());
							state.setComment("Notificado");
							
							soaStateRepository.save(state);						
						}						
					}					
				}			
				else
					LOGGER.info("No hay ventas para notificar");
			}		
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendInventoryNotifications() {
		
		try {
			
			Optional<SoaStateType> toNotifyOp = soaStateTypeRepository.findByCode("POR_NOTIFICAR");
			Optional<SoaStateType> notifiedOp = soaStateTypeRepository.findByCode("NOTIFICADO");
			Optional<SellOutType> invTypeOp = sellOutTypeRepository.findByCode("INV");			
			
			SoaStateType toNotify = null;
			SoaStateType notified = null;
			SellOutType invType = null; 
			
			if (toNotifyOp.isPresent() && notifiedOp.isPresent() && invTypeOp.isPresent()) {
			
				toNotify = toNotifyOp.get();
				notified = notifiedOp.get();
				invType = invTypeOp.get();
				
				// CONSULTA POR LOS INVENTARIOS EN ESTADO POR NOTIFICAR
				List<SellOut> sellOuts = sellOutRepository.findBySoaStateTypeIdAndSellOutTypeId(toNotify.getId(), invType.getId());
				
				if (sellOuts != null && !sellOuts.isEmpty()) {
					
					for (SellOut sellOut : sellOuts) {
						
						Optional<Vendor> vendorOp = companyRepository.findById(sellOut.getVendor().getId());
						Vendor vendor = null;	
						
						if (vendorOp.isPresent()) {
							vendor = vendorOp.get();
							
							JAXBContext jc = getInvJC();
							bbr.b2b.b2blink.commercial.xml.NotificacionInventario.ObjectFactory objFactory = new bbr.b2b.b2blink.commercial.xml.NotificacionInventario.ObjectFactory();
							NotificacionInventario notification = objFactory.createNotificacionInventario();
							notification.setCodproveedor(vendor.getCode());							
							notification.setNombreportal(serviceConfiguration.getPortalName());
							

							// Obtiene string XML para enviarlo a la cola
							Marshaller m = jc.createMarshaller();
							m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
							StringWriter sw = new StringWriter();
							m.marshal(notification, sw);
							String result = sw.toString();

							LOGGER.info("Notificación de carga de Inventario a SOA, Proveedor: " + vendor.getCode());

							// ENVIA EL MENSAJE A LA COLA							
							messageService.send(QueueDefinitions.Q_SOA_OUT, result);						

							// CAMBIA ESTADO SOA DE SELLOUT A NOTIFICADA
							sellOut.setSoaStateType(notified);
							
							sellOutRepository.save(sellOut);
							
							// SE DEJA EN ESTADO NOTIFICADA
							SoaState state = new SoaState();
							state.setSellOut(sellOut);
							state.setSoaStateType(notified);
							state.setWhen(LocalDateTime.now());
							state.setComment("Notificado");
							
							soaStateRepository.save(state);		
						}						
					}					
				}			
				else
					LOGGER.info("No hay inventario para notificar");
			}		
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
