package bbr.b2b.soa.integrator.queue.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.core.MessagePostProcessor;

/** 
 *  
 * <p>Esta clase permite agregar propiedades custom a la cabecera del mensaje.
 * <p>Se debe definir un Map con los valores y pasarlos al constructor de la clase.
 * <p>Por defecto, se agrega un correlationId a todos los mensajes  
 * 
 * 
 * */
public class JMSPropertyManager implements MessagePostProcessor{

	private Map<String, Object> properties = new HashMap<String, Object>();		
	
	public JMSPropertyManager() {
	
	}
	
	public JMSPropertyManager(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	
	@Override
	public Message postProcessMessage(Message message) throws JMSException {
		message.setJMSCorrelationID(UUID.randomUUID().toString());
		
		properties.forEach((k, v) -> {
			try {
				message.setObjectProperty(k, v);
			} catch (JMSException e) {				
				e.printStackTrace();
			}
		});		
				
		return message;
	}
	
	

}
