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
import org.jboss.annotation.IgnoreDependency;

import bbr.b2b.regional.logistic.managers.interfaces.DeletePendingMessageTimerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;

@Stateless(name = "managers/DeletePendingMessageTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeletePendingMessageTimer implements DeletePendingMessageTimerLocal {

	private static Logger logger = Logger.getLogger(DeletePendingMessageTimer.class);

	private static boolean isServiceStarted = false;

	@EJB
	@IgnoreDependency
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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron de limpieza de mensajes enviados exitosamente a MQ");

		isServiceStarted = true;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	public synchronized void timeoutHandler(Timer timer) {
		int messagedeleted = 0;
		try {
			if (!isServiceStarted)
				return;
			
			logger.info("Evento automático de limpieza de mensajes enviados exitosamente a MQ iniciado");
			
			messagedeleted = schmanager.doDeletePendingMessage();
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento automático de limpieza de mensajes enviados exitosamente a MQ falló");
		}
		logger.info("Evento automático de limpieza de mensajes enviados exitosamente a MQ finalizado. Se eliminaron:" + messagedeleted +" registros");
	}

}
