package bbr.b2b.regional.logistic.managers.classes;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.managers.interfaces.PendingMessageTimerLocal;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMessageW;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;

@Stateless(name = "managers/PendingMessageTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMessageTimer implements PendingMessageTimerLocal {

	private static Logger logger = Logger.getLogger(PendingMessageTimer.class);

	private static boolean isServiceStarted = false;

	@EJB
	SchedulerManagerLocal schmanager;

	@Resource
	private SessionContext ctx;

	public synchronized void scheduleTimer(long initialinterval, long milliseconds) {
		// Cancelar todos los timers previamente asociados a este manager
		TimerService ts = ctx.getTimerService();
		// Obtiene todos los timers asociados al bean
		Collection<Timer> timers = ts.getTimers();
		// ... y los cancela
		for (Iterator iterator = timers.iterator(); iterator.hasNext();) {
			Timer timer = (Timer) iterator.next();
			timer.cancel();
		}
		// Crear el timerservice
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron envío de mensajes pendientes a MQ");

		isServiceStarted = true;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	public synchronized void timeoutHandler(Timer timer) {

		try {
			int count = Integer.valueOf(RegionalLogisticConstants.getInstance().getPENDINGMESSAGECOUNT());

			if (!isServiceStarted)
				return;

			PendingMessageW[] pms = schmanager.getPendingMessagesToSend(count);
			for (int i = 0; i < pms.length; i++) {
				schmanager.doSendMessageQueue(pms[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento autom�tico de mensajes pendientes fall�");
		}
		logger.info("Evento autom�tico de mensajes pendientes finalizado");
	}

}
