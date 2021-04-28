package bbr.b2b.logistic.managers.classes;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.data.wrappers.PendingMessageW;
import bbr.b2b.logistic.managers.interfaces.PendingMessageManagerLocal;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;

@Stateless(name = "managers/PendingMessageManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMessageManager implements PendingMessageManagerLocal {

	private static Logger logger = Logger.getLogger(PendingMessageManager.class);

//	private static boolean isServiceStarted = false;
	
	@EJB
	SchedulerManagerLocal schmanager;
	
	@Resource
	private SessionContext ctx;
	
	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
	
	public void doProcess() throws ServiceException{

		try {
//			int count = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("pendingmessagecount"));
			int count = 100;
			
//			if (!isServiceStarted)
//				return;
			
			logger.info("Evento automático de envío de mensajes pendientes a MQ iniciado");
			
			PendingMessageW[] pms = schmanager.getPendingMessagesToSend(count);
			for (int i = 0; i < pms.length; i++) {
				try{
					schmanager.doSendMessageQueue(pms[i]);
				}
				catch(OperationFailedException e){
					logger.error(e.getMessage());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento automático de envío de mensajes pendientes a MQ falló");
		}
		logger.info("Evento automático de envío de mensajes pendientes a MQ finalizado");
	}

}
