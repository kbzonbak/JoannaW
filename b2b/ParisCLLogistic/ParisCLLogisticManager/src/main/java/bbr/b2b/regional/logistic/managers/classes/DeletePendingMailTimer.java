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

import bbr.b2b.regional.logistic.managers.interfaces.DeletePendingMailTimerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;

@Stateless(name = "managers/DeletePendingMailTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeletePendingMailTimer implements DeletePendingMailTimerLocal {

	private static Logger logger = Logger.getLogger(DeletePendingMailTimer.class);

	private static boolean isServiceStarted = false;

	@EJB
	@IgnoreDependency
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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron de limpieza de correos enviados exitosamente");

		isServiceStarted = true;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	public synchronized void timeoutHandler(Timer timer) {
		int maildeleted = 0;
		try {
			if (!isServiceStarted)
				return;
			
			logger.info("Evento automático de limpieza de correos enviados exitosamente iniciado");
			
			maildeleted = schmailmanager.doDeletePendingMails();
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento automático de limpieza de correos enviados exitosamente falló");
		}
		logger.info("Evento automático de limpieza de correos enviados exitosamente finalizado. Se eliminaron:" + maildeleted +" registros");
	}

}
