package bbr.b2b.logistic.managers.classes;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.managers.interfaces.PendingMailManagerLocal;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMailW;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;

@Stateless(name = "managers/PendingMailManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMailManager implements PendingMailManagerLocal {

	private static Logger logger = Logger.getLogger(PendingMailManager.class);
	
	@EJB
	private SchedulerMailManagerLocal schmailmanager;
	
	private javax.ejb.SessionContext mySessionCtx;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	@Resource
	public void setSessionContext(javax.ejb.SessionContext context) {
		this.mySessionCtx = context;
	}
		
	public synchronized void doProcess() throws Exception{
		// Llama a sevicio que envía correos
		try {
			int maxMail = LogisticConstants.getMAX_MAIL_TO_SEND();
			PendingMailW[] pms = schmailmanager.getPendingMailToSend(maxMail);
			for (int i = 0; i < pms.length; i++) {
				schmailmanager.doSendPendingMail(pms[i]);
			}
			logger.info("Evento de envio de correos finalizado");
		
		} catch (Exception e) {
			logger.error("DEMONIO_ENVIO_CORREOS: Error excepcional!");
			e.printStackTrace();
			throw e;
		}
	}
}
