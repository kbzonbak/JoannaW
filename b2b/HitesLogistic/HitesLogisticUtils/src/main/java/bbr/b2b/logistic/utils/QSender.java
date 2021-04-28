package bbr.b2b.logistic.utils;

import java.nio.charset.Charset;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QSender {

	private static QSender _instance;

	// Constructor
	public static synchronized QSender getInstance() {
		if (_instance == null) {
			_instance = new QSender();
		}
		return _instance;
	}

	private QSender() {
	}


	public synchronized void putMessage(String connectionFactory, String destination, String messagecontent) throws NamingException, JMSException {
		putMessage(connectionFactory, destination, messagecontent, null, null, null);
	}
	
	public synchronized void putMessage(String connectionFactory, String destination, String messagecontent, String[] propertyname, String[] propertyvalue) throws NamingException, JMSException {
		putMessage(connectionFactory, destination, messagecontent, propertyname, propertyvalue, null);
	}
	
	
	public synchronized void putMessage(String connectionFactory, String destination, String messagecontent, String[] propertyname, String[] propertyvalue, Charset charset) throws NamingException, JMSException {
		try {
			connectionFactory = "java:jboss/" + connectionFactory;
			destination = "java:jboss/" + destination;
			InitialContext ic = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) ic.lookup(connectionFactory);
			Destination d = (Destination) ic.lookup(destination);
			Connection c = cf.createConnection();
			c.start();
			Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);	
			MessageProducer pr = s.createProducer(d);			
			pr.setDeliveryMode(DeliveryMode.PERSISTENT);			
			javax.jms.Message m = s.createTextMessage(messagecontent);
			
			if(charset != null){
				m.setStringProperty("JMS_IBM_Character_Set", charset.name());
			}
			
			for(int i = 0;propertyname != null && i<propertyname.length;i++){
				if (propertyname[i] != null && !propertyname[i].equals("")) {
					m.setStringProperty(propertyname[i], propertyvalue == null ? "" : propertyvalue[i]);
				}
			}
			m.setJMSType("jms_text");
			pr.send(m);
			c.close();
		} catch (NamingException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (JMSException e2) {
			e2.printStackTrace();
			throw e2;
		}
	}
	
	
	

	
}
