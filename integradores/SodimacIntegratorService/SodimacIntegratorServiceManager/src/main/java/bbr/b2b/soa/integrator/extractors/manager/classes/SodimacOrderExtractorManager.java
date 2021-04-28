package bbr.b2b.soa.integrator.extractors.manager.classes;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.b2blink.dto.CredentialsDTO;
import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.extractors.client.classes.SodimacServiceClient;
import bbr.b2b.soa.integrator.extractors.manager.interfaces.ISodimacOrderExtractorManager;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.utils.JMSPropertyManager;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response.Message;

@Service
@Transactional
public class SodimacOrderExtractorManager implements ISodimacOrderExtractorManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SodimacOrderExtractorManager.class);
		
	@Autowired
	private ServiceConfiguration serviceConfiguration;	

	@Autowired	
	private JMSMessageService messageservice;	
	
	@Autowired
	private VendorRepository companyRepository;
	
	@Autowired
	private SodimacServiceClient sodimacService;
		
	@Override
	public void doProcessOC() throws Exception {
		try {
			requestOrders(serviceConfiguration.getOrderServiceContracted(), "eOC");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void doProcessOD() throws Exception {
		try {
			requestOrders(serviceConfiguration.getOrderServiceContracted(), "eOD");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void doProcessOE() throws Exception {
		try {
			requestOrders(serviceConfiguration.getOrderServiceContracted(), "eOE");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void requestOrders(String service, String requestType) throws Exception {

		String siteName = serviceConfiguration.getSiteName();
				
		try {
			LOGGER.info("Empresa: " + siteName + " SERVICIO DESCARGA ÓRDENES WS");

			//List<InitParamCSDTO> credentials = wsSoaService.getCredentials(siteCode, service);			
			
			// SE VA A BUSCAR LAS CREDENCIALES A LA BD			
			List<Vendor> companies = companyRepository.findByActive(true);
			
			for (Vendor company : companies) {
				
				if(company.getCredentials() == null || company.getCredentials().isEmpty()){
					LOGGER.info("Empresa: " + siteName + " Proveedor: " + company.getName() + " credenciales vacias o nulas");
					continue;
				}
				
				LOGGER.info("Empresa: " + siteName + " Proveedor: " + company.getName() + " descargando órdenes");

				CredentialsDTO credentialBasicDto = JsonUtils.getObjectFromJson(company.getCredentials(), CredentialsDTO.class);					
				
				Map<String, Object> jmsProperties = Map.of("vendorCode", company.getCode());				
				JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
				
				List<Message> messageList = sodimacService.descargaArchivo(credentialBasicDto.getUser(), credentialBasicDto.getPassword(), requestType).getMessage();
				
				// BUSCA POR UN INTERVALO DE TIEMPO
				//LocalDateTime since = LocalDateTime.of(2021, 3, 18, 0, 0, 0);
				//LocalDateTime until = LocalDateTime.of(2021, 3, 19, 0, 0, 0);
				
				//List<Message> messageList = sodimacService.descargaArchivo(credentialBasicDto.getUser(), credentialBasicDto.getPassword(), requestType, since, until).getMessage();
				if (messageList != null && !messageList.isEmpty()) {
					for (Message message : messageList) {
						
						// LOGUEA MENSAJE QUE SE RESCATO DE WS
						LOGGER.info("Mensaje " + requestType + " recibido: " + message.getValue());					
						
						// SE ENVIA A COLA CORRESPONDIENTE Y SE AGREGA EL CÓDIGO DEL VENDEDOR EN LA CABECERA DEL MENSAJE
						if (!message.getCode().equals("1")) {
							
							switch (requestType) {
								case "eOC":
									messageservice.send(QueueDefinitions.Q_EOC, message.getValue(), jmsPropManager);									
									break;
									
								case "eOD":
									messageservice.send(QueueDefinitions.Q_EOD, message.getValue(), jmsPropManager);										
									break;
									
								case "eOE":
									messageservice.send(QueueDefinitions.Q_EOE, message.getValue(), jmsPropManager);
									break;
									
								default:
									messageservice.send(QueueDefinitions.Q_ERROR, message.getValue(), jmsPropManager);								
									break;
								}						
						}								
					}		
				}
				else {
					LOGGER.info("No hay mensajes de tipo " + requestType + " para el Proveedor: " + company.getCode());					
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
