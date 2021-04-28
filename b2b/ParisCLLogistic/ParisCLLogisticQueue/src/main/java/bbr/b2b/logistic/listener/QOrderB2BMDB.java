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
import javax.jms.TextMessage;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.ResourceAdapter;

import bbr.b2b.common.util.Mailer;
import bbr.b2b.logistic.queuehandler.OrderHandlerLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;

@MessageDriven(name = "listeners/QOrderB2BMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/wsmq/q_ordenes"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "messagingType", propertyValue = "javax.jms.MessageListener"),
		@ActivationConfigProperty(propertyName = "transportType", propertyValue = "CLIENT"),
		@ActivationConfigProperty(propertyName = "maxPoolDepth", propertyValue = "1")})		
@TransactionManagement(TransactionManagementType.BEAN)
@ResourceAdapter("wmq-ra-pariscl")
public class QOrderB2BMDB implements MessageListener {
	

	private static Logger logger = Logger.getLogger("CargaMsgesLog");
	
	@Resource
	private MessageDrivenContext mdbContext = null;

	@EJB
	OrderHandlerLocal orderhandler;

	private String businessLogic(String message) {
		try {
			// ---------------------- MAIL -------------------------
			if (RegionalLogisticConstants.getInstance().getSENDMAILTESTCSUD()) {
				String subject = "Carga en B2B Test: Mensaje de Orden de Compra";
				String[] mailreciever = B2BSystemPropertiesUtil.getProperty("CSUDMAIL").split(",");
				String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
				String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
				String text = "Se ha cargado el siguiente mensaje en B2B:\n\n" + message.toString() + "\n\nAtte.\nB2B Paris Chile";
				Mailer.getInstance().sendMailBySession(mailreciever, null, null, mailsender, subject, text, false, null, mailSession);
			}

			orderhandler.handleMessage(message, mdbContext);

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
			if (message instanceof TextMessage) {
				String req = ((TextMessage) message).getText();
				
				// Do something with the messages
				businessLogic(req);
			} else {
				String subject = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "Logistica: Fallo al parsear mensaje de OC";
				try {
					String[] mailreciever = RegionalLogisticConstants.getInstance().getDEVELOPER_MAIL_ERROR();
					String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
					String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
					String text = "El tipo de mensaje no es javax.jms.TextMessage. Mensaje:\n " + message.toString();
					Mailer.getInstance().sendMailBySession(mailreciever, null, null, mailsender, subject, text, false, null, mailSession);
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
			throw (new EJBException(ex));
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
