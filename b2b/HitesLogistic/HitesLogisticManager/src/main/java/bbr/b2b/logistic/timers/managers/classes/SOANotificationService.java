//package bbr.b2b.logistic.timers.managers.classes;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.temporal.ChronoUnit;
//
//import javax.annotation.Resource;
//import javax.ejb.DependsOn;
//import javax.ejb.EJB;
//import javax.ejb.Singleton;
//import javax.ejb.Timeout;
//import javax.ejb.Timer;
//import javax.ejb.TimerService;
//
//import org.apache.log4j.Logger;
//
//import bbr.b2b.common.adtclasses.exceptions.ServiceException;
//import bbr.b2b.logistic.constants.LogisticConstants;
//import bbr.b2b.logistic.managers.interfaces.SOANotificationManagerLocal;
//import bbr.b2b.logistic.timers.managers.interfaces.Scheduler;
//import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
//
//@Singleton
//@DependsOn("managers/SOANotificationManager")
//public class SOANotificationService implements Scheduler {
//
//	@Resource
//	private TimerService timerService;
//
//	private static Logger logger = Logger.getLogger(SOANotificationService.class);
//
//	public static final String CRON_NAME = "Cron de envio notificación SOA";
//
//	public static final String JNDI_TIMER = "java:global/HitesLogisticEAR/HitesLogisticManager/SOANotificationService!bbr.b2b.logistic.timers.managers.interfaces.Scheduler";
//
//	@EJB
//	private SOANotificationManagerLocal soaNotificationManagerLocal;
//
//	@Timeout
//	public void scheduler(Timer timer) throws Exception {
//		logger.info("Se ejecuta " + CRON_NAME);
//
//		// VERIFICA QUE SE PUEDA EJECUTAR
//		if (!Boolean.valueOf(B2BSystemPropertiesUtil.getProperty("ENABLE_SOA_NOTIFICATION_CRON"))) {
//			return;
//		}
//
//		soaNotificationManagerLocal.doProcess();
//	}
//
//	@Override
//	public void initialize(String info) {
//
//		// DETIENE TODOS LOS CRON ASOCIADOS A ESTE SERVICIO
//		stop();
//
//		// SE CONFIGURAN PARAMETROS DE TIEMPO
//		// SE FIJA LA HORA DE INICIO
//
//		long interval_in_minutes = Long.valueOf(B2BSystemPropertiesUtil.getProperty("INTERVAL_SOA_NOTIFICATION_CRON"));
//
//		// SE FIJA EL INTERVALO DE EJECUCION
//		long interval_in_milliseconds = interval_in_minutes * 60 * 1000;
//
//		// SE FIJA EL INSTANTE EN QUE SE EJECUTARA EL CRON
//		LocalDateTime initLocalTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMinutes(1);
//		Instant initInstant = initLocalTime.atZone(ZoneId.systemDefault()).toInstant();
//
//		// SE OBTIENE EL INSTANTE ACTUAL
//		LocalDateTime nowLocalTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
//		Instant nowInstant = nowLocalTime.atZone(ZoneId.systemDefault()).toInstant();
//
//		// CREA EL TIMER
//		timerService.createTimer(initInstant.toEpochMilli() - nowInstant.toEpochMilli(), interval_in_milliseconds, CRON_NAME);
//	}
//
//	@Override
//	public void stop() {
//		logger.info("Detiene algún " + CRON_NAME + " existente");
//		for (Timer timer : timerService.getTimers()) {
//			logger.trace("Detiene timer: " + timer.getInfo());
//			timer.cancel();
//		}
//	}
//}
