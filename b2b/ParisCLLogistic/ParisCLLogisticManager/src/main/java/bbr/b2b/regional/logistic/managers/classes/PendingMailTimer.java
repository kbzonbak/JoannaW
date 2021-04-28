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
import bbr.b2b.regional.logistic.managers.interfaces.PendingMailTimerLocal;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMailW;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;

@Stateless(name = "managers/PendingMailTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMailTimer implements PendingMailTimerLocal {

	private static Logger logger = Logger.getLogger(PendingMailTimer.class);

	private static boolean isServiceStarted = false;

	@EJB
	SchedulerMailManagerLocal schmailmanager;

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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron de envío de correos pendientes");

		isServiceStarted = true;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	public synchronized void timeoutHandler(Timer timer) {

		try {
			int count = Integer.valueOf(RegionalLogisticConstants.getInstance().getPENDINGMAILCOUNT());

			if (!isServiceStarted)
				return;

			PendingMailW[] pms = schmailmanager.getPendingMailsToSend(count);
			for (int i = 0; i < pms.length; i++) {
				schmailmanager.doSendPendingMail(pms[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento autom�tico de envío de correos pendientes fall�");
		}
		logger.info("Evento autom�tico de envío de correos pendientes finalizado");
	}

}
