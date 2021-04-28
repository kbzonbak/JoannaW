package bbr.b2b.soa.integrator.queue.soa.manager.classes;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.facade.entities.Order;
import bbr.b2b.soa.integrator.facade.entities.SoaState;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;

@Component
@Transactional
public class OrderAckMessageProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAckMessageProcessor.class);	

	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;
	
	@Autowired
	private SoaStateRepository soaStateRepository;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	@Autowired
	private OrderRepository orderRepository;
		
	public void processMessage(NotificacionReciboOrden message) {		
		
		try {
			// VALIDA EL MENSAJE
			boolean validation = doValidateOcSoaAckMessage(message);
			
			if (validation) {
				
				// NÚMERO DE ORDEN
				Long ocNumber = Long.valueOf(message.getNumorden());			
				
				// ESTADO OK
				Optional<SoaStateType> okOp = soaStateTypeRepository.findByCode("RECIBIDO_OK");
				
				// ESTADO ERROR
				Optional<SoaStateType> errorOp = soaStateTypeRepository.findByCode("RECIBIDO_ERROR");
				
				// ESTADO ENVIADO
				Optional<SoaStateType> sendedOp = soaStateTypeRepository.findByCode("ENVIADO");				
				
				// VALIDA NOMBRE DE PORTAL
				if (!message.getNombreportal().equals(serviceConfiguration.getPortalName())) {
					LOGGER.info("El Nombre de Portal no corresponde");
				}
				
				SoaStateType ok = null;
				SoaStateType error = null;
				SoaStateType sended = null;
				
				// VALIDA PROVEEDOR
				Vendor vendor = null;			
				Optional<Vendor> vendorOp = companyRepository.findByCode(message.getCodproveedor());
				
				if (vendorOp.isPresent() && okOp.isPresent() && errorOp.isPresent() && sendedOp.isPresent()) {				
					
					vendor = vendorOp.get();
					ok = okOp.get();
					error = errorOp.get();
					sended = sendedOp.get();
					
					// BUSCA LA OC POR NUMERO
					Optional<Order> orderOp = orderRepository.findByOcNumberAndVendorId(ocNumber, vendor.getId());
					
					if (orderOp.isPresent()) {
						
						Order order = orderOp.get();
						
						// CAMBIA EL ESTADO
						String stateMsg = message.getEstado();
					
						// FLOW ID
						String flowId = message.getFlowid();						
						
						Boolean checkFlowId = true;
						
						// SE VALIDA QUE EL ACK CORRESPONDA AL ULTIMO MENSAJE DE OC ENVIADO AL B2BLINK
						if (flowId != null) {
							Optional<SoaState> soaStateOp = soaStateRepository.findFirstByOrderIdAndSoaStateTypeIdOrderByWhenDesc(order.getId(), sended.getId());
							
							if (soaStateOp.isPresent()) {								
								if (!soaStateOp.get().getFlowId().equals(flowId))																						
									checkFlowId = false;								
								
							}						
						}			
						
						// ARCHIVO
						String  fileName = message.getArchivo();
						
						// SE AGREGA UN ESTADO
						SoaState state = new SoaState();
						state.setOrder(order);
						state.setSoaStateType(stateMsg.equalsIgnoreCase("OK") ? ok : error);
						state.setWhen(LocalDateTime.now());
						state.setFlowId(flowId);
						state.setComment(fileName);
						
						soaStateRepository.save(state);							
						
						if (checkFlowId) {
							order.setCurrentSoaStateType(stateMsg.equalsIgnoreCase("OK") ? ok : error); 
							order.setCurrentSoaStateTypeDate(LocalDateTime.now());
							orderRepository.save(order);														
						}								
						
					}else 						
						LOGGER.info("Orden " + ocNumber + " no existe");
				}
				else
					LOGGER.info("En orden " + message.getNumorden() + ". No existe proveedor con rut " + message.getCodproveedor());
					
				
			}else
				LOGGER.info("Mensaje de Notificación ACK inválido");
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	private boolean doValidateOcSoaAckMessage(NotificacionReciboOrden message) {
		
		// NOMBRE PORTAL		
		if (!Optional.ofNullable(message.getNombreportal()).isPresent() || Optional.of(message.getNombreportal()).isEmpty()){	
			
			LOGGER.info("No hay datos para Nombre de Portal");
			return false;
		}				
		// CODIGO PROVEEDOR
		if (!Optional.ofNullable(message.getCodproveedor()).isPresent() || Optional.of(message.getCodproveedor()).isEmpty()){
			
			LOGGER.info("No hay datos para Codigo Proveedor");
			return false;				
		}				
		// CODIGO COMPRADOR
		if (!Optional.ofNullable(message.getCodcomprador()).isPresent() || Optional.of(message.getCodcomprador()).isEmpty()){

			LOGGER.info("No hay datos para Codigo Comprador");
			return false;
			
		}		
		// NUMERO DE OC
		if (!Optional.ofNullable(message.getNumorden()).isPresent() || Optional.of(message.getNumorden()).isEmpty()){
			
			LOGGER.info("No hay datos para Número de OC");
			return false;				
		}		
		// NUMERO DE OC
		try {
			Long.valueOf(message.getNumorden());
		}catch (NumberFormatException e) {
			
			LOGGER.info("El Número de oc no es númerico");
			return false;
		}		
		// ARCHIVO 
		if (!Optional.ofNullable(message.getArchivo()).isPresent() || Optional.of(message.getArchivo()).isEmpty()){
			
			LOGGER.info("No hay datos para Archivo");
			return false;				
		}		
		// ESTADO
		if (!Optional.ofNullable(message.getEstado()).isPresent() || Optional.of(message.getEstado()).isEmpty()){
			
			LOGGER.info("No hay datos para Estado");
			return false;				
		}					
		// ESTADO
		if (!message.getEstado().equalsIgnoreCase("OK") && !message.getEstado().equalsIgnoreCase("ERROR")){			
			
			LOGGER.info("El estado " + message.getEstado() + " no corresponde a un estado válido");
			return false;	
		}				
		
		return true;	
	}
	
}
