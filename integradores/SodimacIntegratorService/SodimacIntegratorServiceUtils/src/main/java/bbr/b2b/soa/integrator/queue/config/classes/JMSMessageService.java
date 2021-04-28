package bbr.b2b.soa.integrator.queue.config.classes;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import bbr.b2b.soa.integrator.queue.utils.JMSPropertyManager;

@Component
public class JMSMessageService {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(String destinationName, String message) {
		send(destinationName, message, new JMSPropertyManager());
	}

	public void send(String destinationName, String message, JMSPropertyManager jmsPropertyManager) {
		jmsTemplate.convertAndSend(destinationName, message, jmsPropertyManager);		
	}
	
	public void send(String destinationName, Message message) {
		jmsTemplate.send(destinationName, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return message;
              }
        });	
	}
	
}
