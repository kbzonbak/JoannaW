package bbr.b2b.logistic.soa.listener;

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
import javax.jms.TextMessage;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import bbr.b2b.common.util.Mailer;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.soa.queuehandler.SOAHandlerLocal;

@MessageDriven(name = "listeners/QSoaB2BMDB", activationConfig = { 
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/q_hites_log_in"), 
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), 
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"), 
		@ActivationConfigProperty(propertyName = "maxMessagesPerSessions", propertyValue = "1"), 
		@ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "useJndi", propertyValue = "true")})
//@ResourceAdapter("activemq-rar-5.14.5-SOA.rar")
@org.jboss.ejb3.annotation.ResourceAdapter("activemq-ra-soageneral")//@org.jboss.ejb3.annotation.ResourceAdapter("activemq-ra-hites")
@TransactionManagement(TransactionManagementType.BEAN)
public class QSoaB2BMDB implements MessageListener {

	//private static LoggingUtil logger = new LoggingUtil(QSoaB2BMDB.class);
	private static Logger logger = Logger.getLogger("SOALog");
		
	@Resource
	private MessageDrivenContext mdbContext = null;

	@EJB
	SOAHandlerLocal soaHandlerLocal;

	private String businessLogic(String message, Long ticketNumber) {
		try {
			soaHandlerLocal.handleMessage(message, mdbContext, ticketNumber);

		} catch (Exception e) {
			logger.info("Excepci�n inesperada: " + e.getLocalizedMessage());
		}
		return ("Response to " + message);
	}

	public javax.ejb.MessageDrivenContext getMessageDrivenContext() {
		return mdbContext;
	}

	public void onMessage(Message message) {
		
		Long ticketNumber = null;
		try {
			try {
				ticketNumber = message.getLongProperty("ticketNumber");
				logger.info("Procesando ticket :" + ticketNumber);
			} catch (NumberFormatException e1) {
				ticketNumber = null;
			}
			logger.info("Mensaje desde SOA recibido");
			if (message instanceof TextMessage) {
				String req = ((TextMessage) message).getText();
				businessLogic(req, ticketNumber);
			} else {
				String subject = LogisticConstants.getInstance().getBUSINESSAREA() + LogisticConstants.getInstance().getCOUNTRYCODE() + "Logistica: Fallo al parsear mensaje de OC Can�nico";
				try {
					Mailer mailer = Mailer.getInstance();
					String[] mailreciever = LogisticConstants.getInstance().getDEVELOPER_MAIL_ERROR();
					// String mailsender = Constants.getPropertyValue("mailsender");
					String mailsender = LogisticConstants.getInstance().getMailSender();
					String mailSession = LogisticConstants.getInstance().getMAIL_SESSION();
					String text = "El tipo de mensaje no es javax.jms.TextMessage. Mensaje:\n " + message.toString();
					//mailer.sendMailBySession(mailreciever, mailreciever, mailsender, subject, text, mailSession);
					mailer.sendMailBySession(mailreciever, null, null, mailsender, subject, text, false, null, mailSession);

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
			UserTransaction ut = mdbContext.getUserTransaction();
			rollback(ut);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new EJBException(ex);
		}
	}
	
	
	private void rollback(UserTransaction usertransaction) {
		try {
			if (usertransaction != null && usertransaction.getStatus() == Status.STATUS_ACTIVE)
				usertransaction.rollback();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
	}
}
