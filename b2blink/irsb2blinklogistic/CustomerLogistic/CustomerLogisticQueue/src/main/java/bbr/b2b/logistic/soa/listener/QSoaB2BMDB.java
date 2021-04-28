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

import bbr.b2b.logistic.soa.queuehandler.SoaHandlerLocal;

@MessageDriven(name = "/listeners/QSoaB2BMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/activemq/queue/q_customerlog_in"),//QL_CUSTOMER_LOG_IN
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "maxMessagesPerSessions", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "useJndi", propertyValue = "true")})
@org.jboss.ejb3.annotation.ResourceAdapter("activemq-ra-soageneral")
@TransactionManagement(TransactionManagementType.BEAN)
public class QSoaB2BMDB implements MessageListener {

	private static Logger logger = Logger.getLogger("SOALog");
		
	@Resource
	private MessageDrivenContext mdbContext = null;

	@EJB
	SoaHandlerLocal soaHandlerLocal;

	private String businessLogic(String message, Long ticketNumber) {
		try {
			soaHandlerLocal.handleMessage(message, mdbContext, ticketNumber);

		} catch (Exception e) {
			logger.info("Excepción inesperada: " + e.getLocalizedMessage());
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
//				String subject = LogisticConstants.getInstance().getBUSINESSAREA() + LogisticConstants.getInstance().getCOUNTRYCODE() + "Logistica: Fallo al parsear mensaje de OC";
//				try {
//					Mailer mailer = Mailer.getInstance();
//					String[] mailreciever = RegionalLogisticConstants.getInstance().getDEVELOPER_MAIL_ERROR();
//					// String mailsender = Constants.getPropertyValue("mailsender");
//					String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
//					String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
//					String text = "El tipo de mensaje no es javax.jms.TextMessage. Mensaje:\n " + message.toString();
//					mailer.sendMailBySession(mailreciever, mailreciever, null, mailsender, subject, text, false, null, mailSession);
//				} catch (Exception e) {
//					logger.error("No se envio el mail del fracaso de envio de mensaje");
//				}
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
