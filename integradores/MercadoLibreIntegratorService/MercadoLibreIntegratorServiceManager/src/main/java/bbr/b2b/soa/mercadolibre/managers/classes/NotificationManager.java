package bbr.b2b.soa.mercadolibre.managers.classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.mercadolibre.constants.ServiceConstants;
import bbr.b2b.soa.mercadolibre.constants.ShipmentMode;
import bbr.b2b.soa.mercadolibre.constants.Topic;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.NotificationData;
import bbr.b2b.soa.mercadolibre.dto.classes.OrderResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.QuestionResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.ShipmentResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.orders.OrderData;
import bbr.b2b.soa.mercadolibre.dto.classes.shipments.ShipmentData;
import bbr.b2b.soa.mercadolibre.entities.Authorization;
import bbr.b2b.soa.mercadolibre.entities.Notification;
import bbr.b2b.soa.mercadolibre.entities.NotificationDetail;
import bbr.b2b.soa.mercadolibre.managers.interfaces.INotificationManager;
import bbr.b2b.soa.mercadolibre.repository.classes.AuthorizationRepository;
import bbr.b2b.soa.mercadolibre.repository.classes.NotificationDetailRepository;
import bbr.b2b.soa.mercadolibre.repository.classes.NotificationRepository;
import bbr.b2b.soa.mercadolibre.utis.JMSMessageService;
import bbr.b2b.soa.mercadolibre.utis.MessageMapperOCInternoImpl;

@Service
@Transactional
public class NotificationManager implements INotificationManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationManager.class);

	private static final DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;

	private static final DateTimeFormatter dtfForFileName = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private NotificationDetailRepository notificationDetailRepository;

	@Autowired
	private AuthorizationRepository authorizationRepository;

	@Autowired
	private MeliRestClient meliClient;

	@Autowired
	private MessageMapperOCInternoImpl messageMapper;

	@Autowired
	private JMSMessageService messageservice;

	@Autowired
	private ServiceConstants serviceConstants;

	@Value("${mdp.jms.notifications.queue.name}")
	private String notificationQueueName;

	@Value("${mdp.jms.outbound.queue.name}")
	private String soaOutboundQueueName;

	@Override
	public void saveNotificationMessage(String jsonBody) {
		// Al recibir notificacion por REST, solo se envia el cuerpo Json a la cola para su procesamiento posterior
		messageservice.send(notificationQueueName, jsonBody);
	}

	@Override
	public Optional<Notification> addNotification(NotificationData data) {
		// Primero se valida que la notificacion no haya sido registrada previamente
		List<Notification> listNotif = notificationRepository.findByResourceAndTopicAndUserId(data.getResource(), data.getTopic(), data.getUser_id());
		if (listNotif != null && !listNotif.isEmpty()) {
			Notification prevNotif = listNotif.get(0);
			LOGGER.info("Se ignora la notificación porque que ya existe un evento similar registrado : {}", prevNotif);
			return Optional.empty();
		}

		Notification notification = new Notification();
		notification.setResource(data.getResource());
		notification.setTopic(data.getTopic());
		notification.setApplicationId(data.getApplication_id());
		notification.setUserId(data.getUser_id());
		notification.setAttempts(data.getAttempts());

		ZonedDateTime sent = ZonedDateTime.parse(data.getSent(), dtf);
		ZonedDateTime received = ZonedDateTime.parse(data.getReceived(), dtf);

		notification.setSent(sent);
		notification.setReceived(received);
		notification.setProcessed(false);

		notification = notificationRepository.save(notification);
		return Optional.of(notification);
	}

	@Override
	public void requestPendingNotificationDetails() {
		// Obtener las notificaciones no procesadas
		List<Notification> list = notificationRepository.findByProcessedOrderById(false);
		for (Notification notification : list) {
			// Por cada notificacion, obtener la autorizacion correspondiente
			Optional<Authorization> optAuth = authorizationRepository.findByCompanyUserId(notification.getUserId());
			if (optAuth == null || optAuth.isEmpty())
				continue;

			// Invocar el servicio para solicitar el detalle de la notificacion con el access token correspondiente
			Authorization authorization = optAuth.get();

			// Dependiendo del tipo de notificacion, invocar el servicio
			switch (notification.getTopic()) {
			case Topic.QUESTIONS:
				Optional<QuestionResponse> optQuestion = meliClient.getQuestionData(notification.getResource(), authorization.getAccessToken());
				if (optQuestion != null && optQuestion.isPresent()) {
					QuestionResponse response = optQuestion.get();
					LOGGER.info("Detalle de pregunta :{}", response.getData());
					// Almacenar la respuesta
					NotificationDetail detail = new NotificationDetail();
					detail.setJsonBody(response.getJsonBody());
					detail.setNotification(notification);
					detail.setWhen(LocalDateTime.now());
					notificationDetailRepository.save(detail);
					// Marcar la notificacion como procesada
					notification.setProcessed(true);
					notification.setDateProcessed(LocalDateTime.now());
					notificationRepository.save(notification);
				}
				break;
			case Topic.ORDERS:
				Optional<OrderResponse> optOrder = meliClient.getOrderData(notification.getResource(), authorization.getAccessToken());
				if (optOrder != null && optOrder.isPresent()) {
					OrderResponse response = optOrder.get();
					LOGGER.info("Detalle de orden :{}", response.getData());
					// Almacenar la respuesta
					NotificationDetail detail = new NotificationDetail();
					detail.setJsonBody(response.getJsonBody());
					detail.setNotification(notification);
					detail.setWhen(LocalDateTime.now());
					notificationDetailRepository.save(detail);
					// Marcar la notificacion como procesada
					notification.setProcessed(true);
					notification.setDateProcessed(LocalDateTime.now());
					notificationRepository.save(notification);
				}
				break;
			case Topic.ITEMS:
				Optional<ItemResponse> optItem = meliClient.getItemData(notification.getResource(), authorization.getAccessToken());
				if (optItem != null && optItem.isPresent()) {
					ItemResponse response = optItem.get();
					LOGGER.info("Detalle de item :{}", response.getData());
					// Almacenar la respuesta
					NotificationDetail detail = new NotificationDetail();
					detail.setJsonBody(response.getJsonBody());
					detail.setNotification(notification);
					detail.setWhen(LocalDateTime.now());
					notificationDetailRepository.save(detail);
					// Marcar la notificacion como procesada
					notification.setProcessed(true);
					notification.setDateProcessed(LocalDateTime.now());
					notificationRepository.save(notification);
				}
				break;
			case Topic.SHIPMENTS:
				Optional<ShipmentResponse> optShipments = meliClient.getShipmentData(notification.getResource(), authorization.getAccessToken());
				if (optShipments != null && optShipments.isPresent()) {
					ShipmentResponse response = optShipments.get();
					LOGGER.info("Detalle de shipment :{}", response.getData());
					// Almacenar la respuesta
					NotificationDetail detail = new NotificationDetail();
					detail.setJsonBody(response.getJsonBody());
					detail.setNotification(notification);
					detail.setWhen(LocalDateTime.now());
					notificationDetailRepository.save(detail);
					// Si el tipo de shipment es "me2" solicitar las etiquetas
					ShipmentData data = response.getData();
					if (data != null && ShipmentMode.ME2.equals(data.getMode())) {
						Optional<File> optFileLabels = meliClient.getShipmentLabelData(data, authorization.getAccessToken());
						if (optFileLabels != null && optFileLabels.isPresent()) {
							// Guardar el archivo en una ruta
							File fileLabel = optFileLabels.get();
							try {
								LOGGER.debug("Temp file: {}", fileLabel.getAbsolutePath());
								String formatDateTime = LocalDateTime.now().format(dtfForFileName);
								String filename = "label_shipment_" + data.getId() + "_" + formatDateTime + ".zip";
								File fileDestino = new File(serviceConstants.getDownloadedFilesFolder(), filename);
								LOGGER.debug("Copying to: {}", fileDestino.getAbsolutePath());
								Files.copy(fileLabel.toPath(), fileDestino.toPath());
							} catch (IOException e) {
								LOGGER.error("No se puedo copiar el archivo", e);
							}
						}
					}
					// Marcar la notificacion como procesada
					notification.setProcessed(true);
					notification.setDateProcessed(LocalDateTime.now());
					notificationRepository.save(notification);
				}
				break;
			}

		}
	}

	@Override
	public void sendUninformedNotificationDetails() {
		// Consultar los detalles de notificacion no informados
		List<NotificationDetail> list = notificationDetailRepository.findByInformed(false);
		for (NotificationDetail detail : list) {
			// Por cada detalle de notificacion, validar si se trata de una OC, si no, salir
			Notification notification = detail.getNotification();
			if (!Topic.ORDERS.equals(notification.getTopic())) {
				continue;
			}
			// obtener el cuerpo json y transformarlo a objeto
			String jsonBody = detail.getJsonBody();
			OrderData data = JsonUtils.getObjectFromJson(jsonBody, OrderData.class);
			// Mapear objeto a String
			Optional<String> optMessage = messageMapper.getOrderNotificationMessage(data);
			// Crear una instancia del objeto de salida (para generar xml)
			if (optMessage == null || optMessage.isEmpty()) {
				continue;
			}
			String messagecontent = optMessage.get();
			// Enviar el string como mensaje a activemq
			LOGGER.info("Mensaje enviado a SOA: \n{}", messagecontent);
			messageservice.send(soaOutboundQueueName, messagecontent);
			// Marcar el detalle de notificacion como enviado
			detail.setInformed(true);
			detail.setDateInformed(LocalDateTime.now());
			notificationDetailRepository.save(detail);
		}

	}

}
