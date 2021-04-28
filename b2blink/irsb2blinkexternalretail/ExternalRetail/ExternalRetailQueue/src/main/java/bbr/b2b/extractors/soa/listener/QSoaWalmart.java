package bbr.b2b.extractors.soa.listener;

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

import bbr.b2b.extractors.soa.queuehandler.SoaHandlerLocal;

@MessageDriven(name = "/listeners/QSoaWalmart", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "QL_CUSTOMER_WALMART"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "maxMessagesPerSessions", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1")})
@org.jboss.ejb3.annotation.ResourceAdapter("activemq-ra-soageneral")
@TransactionManagement(TransactionManagementType.BEAN)
public class QSoaWalmart implements MessageListener {

	private static Logger logger = Logger.getLogger("SOALog");
		
	@Resource
	private MessageDrivenContext mdbContext = null;

	@EJB
	SoaHandlerLocal soaHandlerLocal;

	private String businessLogic(String message) {
		try {
			soaHandlerLocal.handleMessage("CL0701", message, mdbContext);
		} catch (Exception e) {
			logger.info("Excepción inesperada: " + e.getLocalizedMessage());
		}
		return ("Response to " + message);
	}

	public javax.ejb.MessageDrivenContext getMessageDrivenContext() {
		return mdbContext;
	}

	public void onMessage(Message message) {
		try {
			logger.info("Mensaje desde SOA recibido");
			if (message instanceof TextMessage) {
				String req = ((TextMessage) message).getText();
				businessLogic(req);
			} else {
//				
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
