package bbr.b2b.logistic.utils;

import java.nio.charset.Charset;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
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

	public synchronized void putMessage(String connectionFactory, Destination destination, String messagecontent) throws NamingException, JMSException {
		putMessage(connectionFactory, destination, messagecontent, null, 0);
	}

	public synchronized void putMessage(String connectionFactory, Destination destination, String messagecontent, String correlationID, int groupsize) throws NamingException, JMSException {
		try {
			connectionFactory = "java:" + connectionFactory;
			InitialContext ic = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) ic.lookup(connectionFactory);
			Connection c = cf.createConnection();
			c.start();
			Session s = c.createSession(false, 1);
			MessageProducer pr = s.createProducer(destination);
			javax.jms.Message m = s.createTextMessage(messagecontent);
			if (correlationID != null) {
				m.setJMSCorrelationID(correlationID);
				m.setIntProperty("MULE_CORRELATION_GROUP_SIZE", groupsize);
			}
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

	public synchronized void putMessage(String connectionFactory, String destination, String messagecontent, Charset charset) throws NamingException, JMSException {
		Connection c = null;
		try {
			connectionFactory = "java:" + connectionFactory;
			InitialContext ic = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) ic.lookup(connectionFactory);
			Destination d = (Destination) ic.lookup(destination);
			c = cf.createConnection();
			c.start();
			Session s = c.createSession(false, 1);
			MessageProducer pr = s.createProducer(d);
			javax.jms.TextMessage m = s.createTextMessage();
			m.setStringProperty("JMS_IBM_Character_Set", charset.name());
			m.setJMSType("jms_text");
			m.setText(messagecontent);
			pr.send(m);
			c.close();
		} catch (NamingException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (JMSException e2) {
			e2.printStackTrace();
			throw e2;
		} finally {
			if (c != null)
				c.close();
		}
	}	
	
	public synchronized void putMessage(String connectionFactory, String destination, String messagecontent) throws NamingException, JMSException {
		try {
			connectionFactory = "java:" + connectionFactory;
			destination = "java:" + destination;
			InitialContext ic = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) ic.lookup(connectionFactory);
			Destination d = (Destination) ic.lookup(destination);
			Connection c = cf.createConnection();
			c.start();
			Session s = c.createSession(false, 1);
			MessageProducer pr = s.createProducer(d);
			javax.jms.Message m = s.createTextMessage(messagecontent);
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
