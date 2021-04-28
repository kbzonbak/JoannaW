package bbr.b2b.logistic.managers.classes;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.jboss.annotation.IgnoreDependency;

import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.managers.interfaces.PendingMessageManagerLocal;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMessageW;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.scheduler.report.classes.PendingMessageDTO;


@Stateless(name = "managers/PendingMessageManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMessageManager implements PendingMessageManagerLocal {

	private static Logger logger = Logger.getLogger(PendingMessageManager.class);

	@EJB
	@IgnoreDependency
	SchedulerManagerLocal schmanager;
	
	@Resource
	private SessionContext ctx;

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public synchronized void doProcess() throws Exception {
				
		try {
			int count = Integer.parseInt(LogisticConstants.getPENDINGMESSAGECOUNT());
			PendingMessageDTO[] pms = schmanager.getPendingMessagesToSend(count);
			PendingMessageW[] pmsW = new PendingMessageW[pms.length] ;
			
			BeanExtenderFactory.copyProperties(pms, pmsW, PendingMessageW.class);
			for (int i = 0; i < pmsW.length; i++) {
				schmanager.doSendMessageQueue(pmsW[i]);
			}
			
			logger.info("Evento automático de envío de mensajes finalizado");

		} catch (Exception e) {
			logger.error("DEMONIO_ENVIO_MENSAJES: Error excepcional!");
			e.printStackTrace();
			throw e;
		}
	}

}
