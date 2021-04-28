package bbr.b2b.regional.logistic.managers.classes;

import java.util.Calendar;
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
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.datings.managers.interfaces.DatingReportManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.LogisticDatingDailyReportManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.LogisticDatingDailyReportManagerRemote;

@Stateless(name = "managers/LogisticDatingDailyReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LogisticDatingDailyReportManager implements LogisticDatingDailyReportManagerLocal, LogisticDatingDailyReportManagerRemote {

	private static Logger logger = Logger.getLogger(LogisticDatingDailyReportManager.class);

	private static boolean isServiceStarted = false;

	@Resource
	private SessionContext ctx;

	@EJB
	DatingReportManagerLocal datingreportmanagerserver;

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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron de Generación de Reporte diario de Citas");
		isServiceStarted = true;
	}

	@Timeout
	@TransactionTimeout(value = 3600)
	public synchronized void timeoutHandler(Timer timer) throws OperationFailedException, NotFoundException {
		try {

			// ///////
			if (RegionalLogisticConstants.getInstance().getSTOP_DATING_REPORT() && !isServiceStarted)
				return;
			// ///////

			logger.info("Ejecutando cron de reporte de citas");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -1);
			int numberfails = 0;
			for (int i = 1; i < 7; i++) {
				try{
					datingreportmanagerserver.doDailyReportDates(cal.getTime());
					break;
				} catch (Exception e) {					
					Thread.sleep(1000 * 60 * 10);
				}
			}

			if (numberfails == 6) {
				// hacer algo en caso q despues del sexto intento siga fallando
			}

		} catch (Exception e) {
			logger.error("DEMONIO_CAMBIO_ESTADO: Error excepcional!");
			logger.info("******* FIN  CRON *******");
			getCtx().setRollbackOnly();
			e.printStackTrace();
		}
	}

	public SessionContext getCtx() {
		return ctx;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
}
