package bbr.b2b.soa.integrator.queue.listener.classes;

import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.manager.classes.VeVOrderMessageProcessor;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;

@Service
public class VeVOrderMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(VeVOrderMessageListener.class);
	
	@Autowired
	private VeVOrderMessageProcessor processor;
	
	@Autowired
	private JMSMessageService messageService;
	
	
	@JmsListener(destination = QueueDefinitions.Q_EOE, containerFactory = "defaultJmsListenerContainerFactory")
	@Transactional
	public void receiveMessage(@Payload String content,
								@Header(name = JmsHeaders.CORRELATION_ID, defaultValue = "none") String correlationId,
								@Header(name = "vendorCode", defaultValue = "none") String vendorCode,
								Message message) {
		
		try {
				
			if (correlationId.equals("none") || vendorCode.equals("none")) {
				// MENSAJE NO VÁLIDO
				// SE MANDA A LA COLA DE ERROR
				messageService.send(QueueDefinitions.Q_ERROR, message);		
				
			}
			else {
				LOGGER.info("Mensaje recibido - Cola: " + QueueDefinitions.Q_EOE + " - Contenido: " + content + " - Id: " + correlationId);
				
				processor.processEoeMessage(content, vendorCode);
			}  
		} catch (Exception ex) {
          	ex.printStackTrace();
		}	
	}	
}
