package bbr.b2b.soa.integrator.extractors.manager.classes;

import java.time.format.DateTimeFormatter;
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
import bbr.b2b.soa.integrator.extractors.manager.interfaces.ISodimacSalesExtractorManager;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.utils.JMSPropertyManager;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response.Message;

@Service
@Transactional
public class SodimacSalesExtractorManager implements ISodimacSalesExtractorManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SodimacSalesExtractorManager.class);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
	@Autowired
	private ServiceConfiguration serviceConfiguration;	

	@Autowired	
	private JMSMessageService messageservice;
	
	@Autowired
	private SodimacServiceClient sodimacService;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	@Override
	public void doProcessVtaInv() throws Exception {
		try {
			requestVta(serviceConfiguration.getSalesServiceContracted(), "eVTA");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void requestVta(String service, String requestType) throws Exception {

		String siteName = serviceConfiguration.getSiteName();		
		
		try {
			LOGGER.info("Empresa: " + siteName + " SERVICIO DESCARGA VENTAS/INVENTARIO WS");

			//List<InitParamCSDTO> credentials = wsSoaService.getCredentials(siteCode, service);
			
			// SE VA A BUSCAR LAS CREDENCIALES A LA BD			
			List<Vendor> companies = companyRepository.findByActive(true);
			
			for (Vendor company : companies) {
				
				if(company.getCredentials() == null || company.getCredentials().isEmpty()){
					LOGGER.info("Empresa: " + siteName + " Proveedor: " + company.getName() + " credenciales vacias o nulas");
					continue;
				}				
				
				LOGGER.info("Empresa: " + siteName + " Proveedor: " + company.getName() + " descargando ventas e inventario");

				CredentialsDTO credentialBasicDto = JsonUtils.getObjectFromJson(company.getCredentials(), CredentialsDTO.class);
						
				Map<String, Object> jmsProperties = Map.of("vendorCode", company.getCode());				
				JMSPropertyManager jmsPropManager = new JMSPropertyManager(jmsProperties);
				
				List<Message> messageList = sodimacService.descargaArchivo(credentialBasicDto.getUser(), credentialBasicDto.getPassword(), requestType).getMessage();
				
				if (messageList != null && !messageList.isEmpty()) {
					for (Message message : messageList) {
						
						// LOGUEA MENSAJE QUE SE RESCATO DE WS
						LOGGER.info("Mensaje " + requestType + " recibido: " + message.getValue());			
						
						// SE ENVIA A COLA CORRESPONDIENTE Y SE AGREGA EL CÓDIGO DEL VENDEDOR EN LA CABECERA DEL MENSAJE
						if (!message.getCode().equals("1")) {
							
							switch (requestType) {
								case "eVTA":
									// ENVIA A COLA DE VENTAS
									messageservice.send(QueueDefinitions.Q_VTA, message.getValue(), jmsPropManager);	
									
									// ENVIA A COLA DE INVENTARIO
									messageservice.send(QueueDefinitions.Q_INV, message.getValue(), jmsPropManager);	
									break;				
									
								default:
									messageservice.send(QueueDefinitions.Q_ERROR, message.getValue(), jmsPropManager);								
									break;
							}					
						}								
					}	
				}else {
					LOGGER.info("No hay mensajes de tipo " + requestType + " para el Proveedor: " + company.getCode());		
				}						
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
