package bbr.b2b.logistic.listener;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import bbr.b2b.common.util.Mailer;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.queuehandler.ReceptionHandlerLocal;

@MessageDriven(name = "listeners/QReceptionB2BMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/ql_recepcion"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "maxMessagesPerSessions", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "useJndi", propertyValue = "true")})
@org.jboss.ejb3.annotation.ResourceAdapter("activemq-ra-hites")
@TransactionManagement(TransactionManagementType.BEAN)
public class QReceptionB2BMDB implements MessageListener {

	private static Logger logger = Logger.getLogger(QReceptionB2BMDB.class);
	
	@Resource
	private MessageDrivenContext mdbContext = null;

	@EJB
	ReceptionHandlerLocal receptionhandler;

	private String businessLogic(String headervalues, String message) {
		
		try{
			// ---------------------- MAIL -------------------------
			Boolean sendmailmq = LogisticConstants.getSENDMAILMQ();
			if (sendmailmq) {
				String subject = LogisticConstants.getBUSINESSAREA() + LogisticConstants.getCOUNTRYCODE() + "Logistica: Mensaje de Recepción";
				Mailer mailer = Mailer.getInstance();

				String[] mailreciever = LogisticConstants.getMailMQ();				
				String mailsender = LogisticConstants.getMailSender();
				String mailSession = LogisticConstants.getMAIL_SESSION();
				String text = "Mensaje:\n\n" + message.toString();
				mailer.sendMailBySession(mailreciever, mailreciever, mailsender, subject, text, mailSession);
			}
			receptionhandler.handleMessage(headervalues, message, mdbContext);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Excepción inesperada: " + e.getLocalizedMessage());
		}
		return ("Response to " + message);
	}
	
	public javax.ejb.MessageDrivenContext getMessageDrivenContext() {
		return mdbContext;
	}
	
	public void onMessage(Message message) {
		QueueSession responseSession = null;

		try {
			if (message instanceof TextMessage) {
				String req = ((TextMessage) message).getText();
				
				// Buscar en el header del mensaje la propeiedad "message_id"
				String headervalues = message.getStringProperty("message_id");
				if(headervalues == null){
					headervalues = "";
				}
				
				// Do something with the messages
				businessLogic(headervalues, req);

			} else{
				String subject = LogisticConstants.getBUSINESSAREA() + LogisticConstants.getCOUNTRYCODE() + "Logistica: Fallo al parsear mensaje de Recepción";
				try {
					Mailer mailer = Mailer.getInstance();
					String[] mailreciever = LogisticConstants.getDEVELOPER_MAIL_ERROR();

					String mailsender = LogisticConstants.getMailSender();
					String mailSession = LogisticConstants.getMAIL_SESSION();
					String text = "El tipo de mensaje no es javax.jms.TextMessage. Mensaje:\n " + message.toString();
					mailer.sendMailBySession(mailreciever, mailreciever, mailsender, subject, text, mailSession);
				} catch (Exception e) {
					logger.error("No se envio el mail del fracaso de envio de mensaje");
				}
			}
			
			
		} catch (JMSException jmsex) {
			logger.error("MDB::onMessage: JMS Exception occured:");
			jmsex.printStackTrace();

			// =================================================================================
			// When using WSMQ the following is required to gather the MQ
			// underlying exception
			// =================================================================================
			Exception lex = jmsex.getLinkedException();
			if (lex != null) {
				logger.error("MDB::onMessage: JMS Linked Exception:");
				logger.error("========================");
				lex.printStackTrace();
			}
			mdbContext.setRollbackOnly();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw (new EJBException(ex));
		} finally {
			// ALWAYS close the session. It's pooled, so do not worry.
			if (responseSession != null) {
				try {
					responseSession.close();
				} catch (Exception e) {
					// Ignore
					// e.printStackTrace();
				}
			}
		}
	}	
}
