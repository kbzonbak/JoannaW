package bbr.b2b.portal.classes.jms.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;

import cl.bbr.core.classes.messaging.BbrMessage;

public class WSMessageProducer {

	private static WSMessageProducer _instance;

	public static synchronized WSMessageProducer getInstance() {
		if (_instance == null) {
			_instance = new WSMessageProducer();
		}
		return _instance;
	}

	private WSMessageProducer() {
	}
	
	public synchronized void putMessage(String topic, String messagecontent) throws NamingException, JMSException {
		putMessage("", topic, messagecontent, new String[0], new String[0]);
	}	
	
	public synchronized void putMessage(String connectionFactory, String topic, String messagecontent) throws NamingException, JMSException {
		putMessage(connectionFactory, topic, messagecontent, new String[0], new String[0]);
	}	
	public synchronized void putMessage(String connectionFactory, String topic, String messagecontent, String[] propertyname, String[] propertyvalue) throws NamingException, JMSException {
		try {
			
			ConnectionFactory cf = null;
			
			if (connectionFactory.isEmpty()){
				String brokerURL = System.getProperty("brokerAMQ");
				
				cf = new ActiveMQConnectionFactory(brokerURL);
			}
			else{
				
				connectionFactory = "java:jboss/" + connectionFactory;			
				InitialContext ic = new InitialContext();
				cf = (ConnectionFactory) ic.lookup(connectionFactory);
			}					
			
			Connection connection = cf.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Topic top = session.createTopic(topic);
	        MessageProducer producer = session.createProducer(top);	
					
			Message message = session.createTextMessage(messagecontent);
			
			for(int i = 0;propertyname != null && i<propertyname.length;i++){
				if (propertyname[i] != null && !propertyname[i].equals("")) {
					message.setStringProperty(propertyname[i], propertyvalue[i] == null ? "" : propertyvalue[i]);
				}
			}
			message.setJMSType("jms_text");
			producer.send(message);
			connection.close();
		} catch (JMSException e2) {
			e2.printStackTrace();
			throw e2;
		}
	}	
	
	public synchronized void putMessage(String topic, BbrMessage messagecontent) throws NamingException, JMSException {
		putMessage("", topic, messagecontent, new String[0], new String[0]);
	}
	
	public synchronized void putMessage(String connectionFactory, String topic, BbrMessage messagecontent) throws NamingException, JMSException {
		putMessage(connectionFactory, topic, messagecontent, new String[0], new String[0]);
	}
	
	public synchronized void putMessage(String connectionFactory, String topic, BbrMessage messagecontent, String[] propertyname, String[] propertyvalue) throws NamingException, JMSException {
		try {
			
			ConnectionFactory cf = null;
			
			if (connectionFactory.isEmpty()){
				String brokerURL = System.getProperty("brokerAMQ");
				
				cf = new ActiveMQConnectionFactory(brokerURL);
			}
			else{
				
				connectionFactory = "java:jboss/" + connectionFactory;			
				InitialContext ic = new InitialContext();
				cf = (ConnectionFactory) ic.lookup(connectionFactory);
			}		
			
			Connection connection = cf.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Topic top = session.createTopic(topic);
	        MessageProducer producer = session.createProducer(top);	
					
			ObjectMessage message = session.createObjectMessage(messagecontent);
						
			for(int i = 0; propertyname != null && i < propertyname.length;i++) {
				if (propertyname[i] != null && !propertyname[i].equals("")) {
					message.setStringProperty(propertyname[i], propertyvalue[i] == null ? "" : propertyvalue[i]);
				}
			}
			producer.send(message);
			connection.close();

		} catch (JMSException e2) {
			e2.printStackTrace();
			throw e2;
		}
	}	
}
