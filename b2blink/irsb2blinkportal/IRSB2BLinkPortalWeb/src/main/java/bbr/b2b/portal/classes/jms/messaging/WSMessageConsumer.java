package bbr.b2b.portal.classes.jms.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;

import bbr.b2b.portal.classes.factory.EJBFactory;
import cl.bbr.core.classes.messaging.BbrMessage;

public class WSMessageConsumer implements MessageListener{	
	
	private static WSMessageConsumer _instance;

	// Constructor
	public static synchronized WSMessageConsumer getInstance(String connFactoryStr, String topicStr) {
		if (_instance == null) {
			_instance = new WSMessageConsumer();
			_instance.startConsumer(connFactoryStr, topicStr);
		}
		return _instance;
	}
	
	public static synchronized WSMessageConsumer getInstance(String topicStr) {
		if (_instance == null) {
			_instance = new WSMessageConsumer();
			_instance.startConsumer("", topicStr);
		}
		return _instance;
	}

	private WSMessageConsumer() {

	}

	public void startConsumer(String connFactoryStr, String topicStr)
	{
		try{
			
			ConnectionFactory connFactory = null;
			
			if (connFactoryStr.isEmpty()){
				String brokerURL = System.getProperty("brokerAMQ");
				connFactory = new ActiveMQConnectionFactory(brokerURL);		
			}
			else{
				connFactoryStr = "java:jboss/" + connFactoryStr;
				InitialContext initialContext = new InitialContext();
				connFactory = (ConnectionFactory) initialContext.lookup(connFactoryStr);				
			}			
			
			Long timestamp = System.nanoTime();				
			Connection connection = connFactory.createConnection();
			connection.setClientID("clientID" + timestamp);
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(topicStr);
			MessageConsumer consumer = session.createDurableSubscriber(topic, timestamp.toString());

			System.out.println("Creó consumer");

			consumer.setMessageListener(_instance);	
		}catch (Exception e){
			e.printStackTrace();
		}		
	}	

	@Override
	public void onMessage(Message message) {
		try {
			if(message != null)
			{
				if (message instanceof TextMessage) 
				{
					TextMessage textMessage = (TextMessage) message;     			  
					textMessage.getText();   
				}
				else if (message instanceof ObjectMessage) 
				{
					ObjectMessage objectMessage = (ObjectMessage)message;

					// ACCION A DETERMINAR
					if(objectMessage.getObject() != null && objectMessage.getObject() instanceof BbrMessage)
					{
						BbrMessage bbrMessage = (BbrMessage)objectMessage.getObject();
						
						System.out.println("Llegó mensaje: " + bbrMessage.getDescription());

						EJBFactory.getMessageEJBFactory().getAppMessagesMngrLocal().sendMessageToAllOnlineUsers(bbrMessage);	
					}
				}	
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
