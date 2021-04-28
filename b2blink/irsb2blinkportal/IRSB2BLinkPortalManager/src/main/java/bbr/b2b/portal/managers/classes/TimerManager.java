package bbr.b2b.portal.managers.classes;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.portal.utils.FileHandlerUtils;

@Stateless(name = "managers/TimerManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TimerManager implements TimerManagerLocal{

	private @Resource
	SessionContext ctx;

	private static boolean isServiceStarted = false;

	public synchronized void scheduleTimer(long initialinterval, long milliseconds) {
		// Cancelar todos los timers previamente asociados a este manager
		TimerService ts = ctx.getTimerService();
		Collection<Timer> timers = ts.getTimers();
		for (Iterator<Timer> iterator = timers.iterator(); iterator.hasNext();) {
			Timer timer = (Timer) iterator.next();
			timer.cancel();
		}
		// Crear el timerservice
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "TimerManager");
		isServiceStarted = true;
	}

	@Timeout
	public synchronized void timeoutHandler(Timer timer) {
		if (!isServiceStarted)
			return;
		//LoggingUtil.info("------------------------------------------------------------");
		//LoggingUtil.info("* Evento Timer Recibido: " + timer.getInfo());
		//LoggingUtil.info("------------------------------------------------------------");
		//System.out.println("prueba");
		
		// Borrar los archivos que tengan más de 5 minutos de vida
		FileHandlerUtils.getInstance().deleteOldFiles(5);

	}

}
