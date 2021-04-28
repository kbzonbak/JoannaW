package bbr.b2b.soa.integrator.queue.soa.manager.classes;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle;
import bbr.b2b.common.dto.classes.BaseResultDTO;
import bbr.b2b.common.dto.classes.BaseResultsDTO;
import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.b2blink.dto.CredentialsDTO;
import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.extractors.client.classes.SodimacServiceClient;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_response.Message;

@Component
@Transactional
public class StockUpdateMessageProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockUpdateMessageProcessor.class);	

	private static JAXBContext jc = null;
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RespuestaTicket");
		return jc;
	}
		
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	@Autowired
	private JMSMessageService messageService;	
	
	@Autowired
	private SodimacServiceClient sodimacService;
	
	public void processMessage(InventarioProveedor message, String ticketNumber) {
		
		String status = "";
		String statusMessage = "";
		
		BaseResultsDTO results = new BaseResultsDTO();
		
		try {	
			
			// TICKETNUMBER
			if (!ticketNumber.equals("none")) {
								
				String vendorCode = message.getVendedor().getCodigo().trim();
								
				Optional<Vendor> vendorOp = companyRepository.findByCode(vendorCode);
				
				if (vendorOp.isPresent()) {
					
					Vendor vendor = vendorOp.get();
					
					LOGGER.info("Ejecutando actualización de stock para el proveedor " + vendor.getName() + "Portal: " + serviceConfiguration.getPortalName());
					
					CredentialsDTO credentialBasicDto = JsonUtils.getObjectFromJson(vendor.getCredentials(), CredentialsDTO.class);
					
					List<Message> messageList = sodimacService.cargaArchivo(credentialBasicDto.getUser(), credentialBasicDto.getPassword(), message).getMessage();
					
					if (messageList == null || messageList.isEmpty()) {
						results.setStatuscode("-1");
						results.setStatusmessage("Error intentando actualizar stock");
					}
					else {
						
						results.setStatuscode("0");
						results.setStatusmessage("Inventario procesado exitosamente");
						
						BaseResultDTO[] detalles = null;
						
						// PROCESA RESPONSE
						int errorsize = 0;						
						for (Message mes : messageList) {
							LOGGER.info("SKU: " + mes.getCode() + " RespuestaWS: " +mes.getValue());
							
							if(!mes.getValue().equals("Carga de archivo exitosa")){
								errorsize++;
							}
						}
						
						if (errorsize > 0) {
							String error = "";
							status = "-1";
							statusMessage = "Error al procesar mensaje de inventario";
							detalles = new BaseResultDTO[errorsize];
							int num = 0;
							for (Message mes : messageList) {
								if(!mes.getValue().equals("Carga de archivo exitosa")){
									BaseResultDTO baseResultDTO = new BaseResultDTO();
									baseResultDTO.setStatuscode("-1");
									baseResultDTO.setStatusmessage(mes.getValue());
									detalles[num] = baseResultDTO;
									error = mes.getCode() + ", ";
									num++;
								}
							}
							LOGGER.error("El stock de los siguientes SKU no se ha podido actualizar. " + error);
						}
						
						results.setBaseresults(detalles);
						results.setStatuscode(status);
						results.setStatusmessage(statusMessage);						
					}
					
					// ENVIA RESPUESTA DE TICKET
					sendTicketResponse(vendorCode, Long.valueOf(ticketNumber), results);
					
					LOGGER.info("Finaliza proceso de actualización de stock para el proveedor " + vendor.getName() + "Portal: " + serviceConfiguration.getPortalName());				
				}				
			}		
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}	
	
	private void sendTicketResponse(String vendorCode, Long ticketNumber, BaseResultsDTO results) {
		
		// RESPUESTA HACIA SOA
		ObjectFactory objFactory = new ObjectFactory();
		
		RespuestaTicket respuestaTicket = objFactory.createRespuestaTicket();
		respuestaTicket.setTicketnumber(ticketNumber != null ? ticketNumber : 0);
		respuestaTicket.setCodproveedor(vendorCode);
		respuestaTicket.setNombreportal(serviceConfiguration.getPortalName());
		respuestaTicket.setServicename("AST");
		respuestaTicket.setEstadoticket(results.getStatuscode());
		respuestaTicket.setDescripcion(results.getStatusmessage());
		respuestaTicket.setUrl("");

		// DETALLES DEL TICKET
		Detalles detallesTicket = objFactory.createRespuestaTicketDetalles();
		List<Detalle> detalleTicketList = detallesTicket.getDetalle();
		
		if (results.getBaseresults() != null) {
			BaseResultDTO[] errors = results.getBaseresults();

			if (errors != null) {
				for (int i = 0; i < errors.length; i++) {
					Detalle detalleticket = objFactory.createRespuestaTicketDetallesDetalle();
					detalleticket.setTipo("linea");
					detalleticket.setCodigo(errors[i].getStatuscode());
					detalleticket.setEstado("");
					detalleticket.setDescripcion(errors[i].getStatusmessage());
					detalleTicketList.add(detalleticket);
				}
			}
			respuestaTicket.setDetalles(detallesTicket);
		}

		try {
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(respuestaTicket, sw);
			String result = sw.toString();
					 
			// ENVIA MENSAJE A SOA
			messageService.send(QueueDefinitions.Q_SOA_OUT, result);	
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
