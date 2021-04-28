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
import bbr.b2b.soa.integrator.queue.manager.classes.PurchaseOrderMessageProcessor;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;

@Service
public class PurchaseOrderMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderMessageListener.class);
	
	@Autowired
	private PurchaseOrderMessageProcessor processor;
	
	@Autowired
	private JMSMessageService messageService;
	
	
	@JmsListener(destination = QueueDefinitions.Q_EOC, containerFactory = "defaultJmsListenerContainerFactory")
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
				LOGGER.info("Mensaje recibido - Cola: " + QueueDefinitions.Q_EOC + " - Contenido: " + content + " - Id: " + correlationId);				
				
				processor.processEocMessage(content, vendorCode);
			}  
		} catch (Exception ex) {
          	ex.printStackTrace();
		}	
	}	
}
