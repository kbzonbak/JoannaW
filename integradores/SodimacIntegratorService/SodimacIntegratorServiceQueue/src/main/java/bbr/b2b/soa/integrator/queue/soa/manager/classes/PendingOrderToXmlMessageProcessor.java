package bbr.b2b.soa.integrator.queue.soa.manager.classes;

import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ListaProveedoresOrdenesPendientes.Proveedores.Proveedor.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes.Proveedores.Proveedor;
import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.facade.entities.Order;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;

@Component
@Transactional
public class PendingOrderToXmlMessageProcessor {
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PendingOrderToXmlMessageProcessor.class);	

	private static JAXBContext jc = null;
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.ListaProveedoresOrdenesPendientes");
		return jc;
	}
		
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private OrderRepository orderRepository;
		
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;	
	
	@Autowired
	private VendorRepository companyRepository;	
	
	@Autowired
	private JMSMessageService messageService;	
	
	public void processMessage(SolicitudProveedoresOrdenesPendientes message) {
		
		Map<Long, Vendor> vendorIds = new HashMap<>();
		Map<Long, ProviderData> vendorData = new HashMap<Long, ProviderData>();
		String result;	
		
		try {	
			
			// VALIDA EL MENSAJE
			boolean validation = doValidateOcPendingMessage(message);
			
			if (validation) {
				
				// ESTADOS SOA
				List<SoaStateType> soaStates = soaStateTypeRepository.findAll();				
				
				Map<Long, SoaStateType> soaStateTypeMap = soaStates.stream().collect(Collectors.toMap(SoaStateType::getId, soaStateType -> soaStateType));
				
				// ESTADO OK
				Optional<SoaStateType> okOp = soaStateTypeRepository.findByCode("RECIBIDO_OK");			
						
				// PROVEEDORES
				List<Proveedor> provList = message.getProveedores().getProveedor();
				
				for (Proveedor prov : provList){
					
					// TIEMPO DE TOLERANCIA ANTES DE ALERTAR
					Integer soaPendingReceptionTime = prov.getTiempoMaximoFinFlujo() > 0 ? prov.getTiempoMaximoFinFlujo() : serviceConfiguration.getSoaPendingReceptionTime();
										
					Optional<Vendor> vendorOp = companyRepository.findByCode(prov.getCodigo());
					
					if (!vendorOp.isPresent()) {
						LOGGER.info("No existe proveedor con código " + prov.getCodigo());
						continue;
					}
					
					Vendor vendor = vendorOp.get();
					
					if (!vendorIds.containsKey(vendor.getId())){
						vendorIds.put(vendor.getId(), vendor);
						
						try {
							
							ProviderData providerData = new ProviderData();
							providerData.setActivationDate(LocalDateTime.parse(prov.getFechaActivacion(), dtf).toLocalDate());
							providerData.setPendingTime(soaPendingReceptionTime);		
							providerData.setContractedStates(null); // TODO: EN SODIMAC NO SE MANEJAN ESTADOS DE ORDEN
							
							vendorData.put(vendor.getId(), providerData);
						} catch (DateTimeParseException e) {
							e.printStackTrace();
						}	
					}			
				}						
				
				if (!vendorIds.isEmpty() && okOp.isPresent()) {
					
					SoaStateType ok = okOp.get();
								
					ObjectFactory objFactory = new ObjectFactory();
					ListaProveedoresOrdenesPendientes qlistaPendProv = objFactory.createListaProveedoresOrdenesPendientes();
					
					Proveedores proveedores = objFactory.createListaProveedoresOrdenesPendientesProveedores();

					for (Long vendorId : vendorIds.keySet()){
						
						LocalDateTime soaPendingTime = LocalDateTime.now().minusMinutes(vendorData.get(vendorId).getPendingTime());						
						
						Proveedores.Proveedor proveedor = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedor();
						
						proveedor.setCodigo(vendorIds.get(vendorId).getCode());
						proveedor.setNombre(vendorIds.get(vendorId).getName());

						// CONSULTA POR CANTIDAD DE OC PENDIENTES DE RECEPCIONAR DESDE SOA
						List<Order> orders = orderRepository.getPendingOrdersByVendor(ok.getId(), soaPendingTime, vendorId, vendorData.get(vendorId).getActivationDate());
						
						logPendingSOAOrder(orders, vendorIds.get(vendorId), soaStateTypeMap);
						proveedor.setNumpendientes(orders.size());
						
						Integer maxPendingOrders = serviceConfiguration.getMaxPendingOrders();
						
						Integer maxOrders = orders.size() < maxPendingOrders ? orders.size() : maxPendingOrders;
						
						Detalles detalles = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedorDetalles();
						List<Detalle> detallesList = detalles.getDetalle();
						
						// DETALLES
						for (int i = 0; i < maxOrders; i++) {

							Order order = orders.get(i);
							
							Detalle detalle = objFactory.createListaProveedoresOrdenesPendientesProveedoresProveedorDetallesDetalle();
													
							String fecha = dtf.format(order.getCurrentSoaStateTypeDate());

							detalle.setUltimoCambioEstadoSOA(fecha);
							detalle.setNumeroOC(order.getOcNumber().toString());
							detalle.setEstadoSoaOC(soaStateTypeMap.get(order.getCurrentSoaStateType().getId()).getCode());
							detalle.setEstadoOC(getOrderState(order));
							detalle.setTipoOC(getOrderType(order));
							detallesList.add(detalle);
						}
						
						if(detallesList != null && detallesList.size() > 0)
							proveedor.setDetalles(detalles);
						
						proveedores.getProveedor().add(proveedor);							
					}
					
					qlistaPendProv.setNombreportal(serviceConfiguration.getPortalName());
					qlistaPendProv.setCodcomprador(message.getCodcomprador());
					qlistaPendProv.setProveedores(proveedores);
					
					// OBTIENE EL XML FINAL					
					JAXBContext jc = getJC();
					Marshaller m = jc.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
					StringWriter sw = new StringWriter();
					m.marshal(qlistaPendProv, sw);
					result = sw.toString();	
										
					LOGGER.info("Mensaje ListaProveedoresOrdenesPendientes: " + result);
					
					// ENVIA A LA COLA DE SOA						
					messageService.send(QueueDefinitions.Q_SOA_OUT, result);						
				}
			}	
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	private boolean doValidateOcPendingMessage(SolicitudProveedoresOrdenesPendientes message) {

		// CODIGO COMPRADOR
		if (!Optional.ofNullable(message.getCodcomprador()).isPresent() || Optional.of(message.getCodcomprador()).isEmpty()){

			LOGGER.info("No hay datos para Codigo Comprador");
			return false;
			
		}	
		// CODIGO PROVEEDORES
		if (!Optional.ofNullable(message.getProveedores()).isPresent() || Optional.of(message.getProveedores()).isEmpty() || 
			!Optional.ofNullable(message.getProveedores().getProveedor()).isPresent() || Optional.of(message.getProveedores().getProveedor()).isEmpty()){
			
			LOGGER.info("No hay datos para Codigo Proveedores");
			return false;				
		}				
		// CODIGO PROVEEDORES
		for (int i = 0; i < message.getProveedores().getProveedor().size(); i++){
			if (message.getProveedores().getProveedor().get(i) == null ||
				message.getProveedores().getProveedor().get(i).getCodigo().equals("")){
				
				LOGGER.info("No hay datos para Codigo Proveedor");
				return false;
			}			
			
			// VALIDA QUE LA FECHA TENGA FORMATO VÁLIDO
			try {
				dtf.parse(message.getProveedores().getProveedor().get(i).getFechaActivacion());
			} catch (DateTimeParseException e) {
				
				LOGGER.info("El formato de la fecha de activación no es válido, yyyy-MM-dd HH:mm:ss z");
				return false;				
			}			
		}		
		
		return true;	
	}
	
	private void logPendingSOAOrder(List<Order> pendingOc, Vendor vendor, Map<Long, SoaStateType> soaStateTypeMap){
		for (Order order : pendingOc) {
			LOGGER.info("OC a Revisar:" + order.getOcNumber() + " - " + dtf.format(order.getEmittedDate()) +" - " + vendor.getCode() + " - " + soaStateTypeMap.get(order.getCurrentSoaStateType().getId()).getCode());
		}		
	}	
	
	private String getOrderType(Order order) {
		
		if (order.getVev()) {
			if (order.getDeliveryTo().equalsIgnoreCase("Cliente"))
				return "VeV PD";
			else
				return "VeV CD";
			
		}else {
			return "Retail";			
		}		
	}
	
	private String getOrderState(Order order) {
		
		if (order.getVev()) {
			return "LIBERADA";
			
		}else {
			if (Optional.ofNullable(order.getEodCreatedDate()).isPresent() && !Optional.of(order.getEodCreatedDate()).isEmpty())
				return "LIBERADA";			
			else 
				return "SIN PREDISTRIBUCION";	
		}		
	}
	
	private class ProviderData{
		
		private LocalDate activationDate;
		private Integer pendingTime;
		private String[] contractedStates;
		
		public LocalDate getActivationDate() {
			return activationDate;
		}
		public void setActivationDate(LocalDate activationDate) {
			this.activationDate = activationDate;
		}
		public Integer getPendingTime() {
			return pendingTime;
		}
		public void setPendingTime(Integer pendingTime) {
			this.pendingTime = pendingTime;
		}
		public String[] getContractedStates() {
			return contractedStates;
		}
		public void setContractedStates(String[] contractedStates) {
			this.contractedStates = contractedStates;
		}		
	}	
}
