package bbr.b2b.soa.mercadolibre.managers.classes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes.SolicitudOrdenes;
import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.mercadolibre.constants.Topic;
import bbr.b2b.soa.mercadolibre.dto.classes.orders.OrderData;
import bbr.b2b.soa.mercadolibre.entities.Company;
import bbr.b2b.soa.mercadolibre.entities.NotificationDetail;
import bbr.b2b.soa.mercadolibre.managers.interfaces.ISolicitudOrdenesMessageProcessor;
import bbr.b2b.soa.mercadolibre.repository.classes.CompanyRepository;
import bbr.b2b.soa.mercadolibre.repository.classes.NotificationDetailRepository;
import bbr.b2b.soa.mercadolibre.utis.JMSMessageService;
import bbr.b2b.soa.mercadolibre.utis.MessageMapperOCInternoImpl;

@Service
@Transactional
public class SolicitudOrdenesMessageProcessor implements ISolicitudOrdenesMessageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SolicitudOrdenesMessageProcessor.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private NotificationDetailRepository notificationDetailRepository;

	@Autowired
	private MessageMapperOCInternoImpl messageMapper;

	@Autowired
	private JMSMessageService messageservice;

	@Value("${mdp.jms.outbound.queue.name}")
	private String soaOutboundQueueName;

	@Override
	public void processMessage(SolicitudOrdenes solicitudOrdenes) {
		// 1) Buscar la empresa asociada por código
		Optional<Company> optCompany = companyRepository.findByRut(solicitudOrdenes.getCodproveedor());
		if (optCompany.isEmpty()) {
			LOGGER.error("No se encontró una empresa con el código : {}", solicitudOrdenes.getCodproveedor());
			return;
		}
		Company company = optCompany.get();
		// ANALIZAMOS QUE CONSULTA DE OC SE REALIZARA EN BASE AL MENSAJE
		// requestType = 1 --> POR DEFECTO BUSCA OCS NOTIFICADAS Y RECIBIDAS CON ERROR
		// requestType = 2 --> BUSCA OCS POR ESTADO SOA ESPECIFICO
		// requestType = 3 --> BUSCA OCS POR NUMEROS
		int requestType = getRequestTypeOfMessage(solicitudOrdenes);

		List<NotificationDetail> list = null;

		switch (requestType) {
		case 1:
			list = notificationDetailRepository.findByCompanyUserIdAndTopicAndSent(company.getUserId(), Topic.ORDERS, false);
			break;

		case 2:
			list = notificationDetailRepository.findByCompanyUserIdAndTopicAndSent(company.getUserId(), Topic.ORDERS, false);
			break;

		case 3:
			List<String> resources = solicitudOrdenes.getDocumentos().getDocumento().stream().map((doc) -> "/orders/" + doc.getId()).collect(Collectors.toList());
			list = notificationDetailRepository.findByCompanyUserIdAndTopicAndResources(company.getUserId(), Topic.ORDERS, resources);
			break;

		default:
			break;
		}

		for (NotificationDetail detail : list) {
			// obtener el cuerpo json y transformarlo a objeto
			String jsonBody = detail.getJsonBody();
			OrderData data = JsonUtils.getObjectFromJson(jsonBody, OrderData.class);
			// 3) Construir mensaje de OC Interno y enviarlo a la cola de entrada de SOA
			Optional<String> optMessage = messageMapper.getOrderMessage(data);
			// Crear una instancia del objeto de salida (para generar xml)
			if (optMessage == null || optMessage.isEmpty()) {
				continue;
			}
			String messagecontent = optMessage.get();
			// Enviar el string como mensaje a activemq
			LOGGER.info("Mensaje enviado a SOA: \n{}", messagecontent);
			messageservice.send(soaOutboundQueueName, messagecontent);
			// 4) Marcar el detalle de notificacion como enviado y poner fecha de envio
			detail.setSent(true);
			detail.setDateSent(LocalDateTime.now());
			notificationDetailRepository.save(detail);
		}
	}

	private int getRequestTypeOfMessage(SolicitudOrdenes message) {
		int requestType = 1;
		if (message.getEstadoSoa() != null && !message.getEstadoSoa().isEmpty())
			requestType = 2;
		else if (message.getDocumentos() != null && message.getDocumentos().getDocumento() != null && !message.getDocumentos().getDocumento().isEmpty())
			requestType = 3;
		return requestType;
	}

}
