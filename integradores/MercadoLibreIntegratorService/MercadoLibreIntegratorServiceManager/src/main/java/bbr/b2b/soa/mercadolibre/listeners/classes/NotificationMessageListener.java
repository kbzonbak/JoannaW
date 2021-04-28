package bbr.b2b.soa.mercadolibre.listeners.classes;

import java.util.Optional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import bbr.b2b.common.utils.JsonUtils;
import bbr.b2b.soa.mercadolibre.dto.classes.NotificationData;
import bbr.b2b.soa.mercadolibre.entities.Notification;
import bbr.b2b.soa.mercadolibre.managers.classes.NotificationManager;

@Component
@ConditionalOnBean(name = "primaryJMSConfiguration")
@ConditionalOnProperty(prefix = "mdp.jms.notifications.queue", value = "name")
public class NotificationMessageListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationMessageListener.class);

	@Autowired
	private NotificationManager notificationManager;

	@Override
	@JmsListener(destination = "${mdp.jms.notifications.queue.name}", containerFactory = "defaultJmsListenerContainerFactory")
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				String content = ((TextMessage) message).getText();
				LOGGER.info("Mensaje recibido: '{}'", content);

				NotificationData data = JsonUtils.getObjectFromJson(content, NotificationData.class);
				LOGGER.info("Notificacion recibida : {}", data);
				// Registrar la notificacion en la BD
				Optional<Notification> optNotif = notificationManager.addNotification(data);
				if (optNotif != null && optNotif.isPresent()) {
					Notification notif = optNotif.get();
					LOGGER.info("Notificacion creada, ID : {}", notif.getId());
				}

			} catch (JMSException e) {
				LOGGER.error("UNABLE TO READ MESSAGE PAYLOAD", e);
			}
		} else {
			LOGGER.error("UNSUPPORTED MESSAGE TYPE");
		}
	}

}