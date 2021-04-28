package bbr.b2b.soa.mercadolibre.utis;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(name = "primaryJMSConfiguration")
public class JMSMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JMSMessageService.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(String destinationName, String message) {
		LOGGER.debug("sending message='{}'", message);
		jmsTemplate.convertAndSend(destinationName, message);
	}

	public void send(String destinationName, Message message) {
		jmsTemplate.send(destinationName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return message;
			}
		});
	}

	public String receiveMessage(String destinationName) throws JMSException {
		String result = null;
		Message message = jmsTemplate.receive(destinationName);
		if (message != null && message instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) message;
			result = txtMsg.getText();
			LOGGER.debug("message receive :", result);
		}
		return result;
	}
}