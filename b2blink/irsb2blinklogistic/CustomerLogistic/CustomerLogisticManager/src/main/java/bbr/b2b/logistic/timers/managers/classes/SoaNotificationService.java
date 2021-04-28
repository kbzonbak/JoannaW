//package bbr.b2b.logistic.timers.managers.classes;
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
//import bbr.b2b.logistic.managers.interfaces.SOANotificationManagerLocal;
//import bbr.b2b.logistic.timers.managers.interfaces.Scheduler;
//
//@Singleton
//@DependsOn("managers/SoaNotificationManager")
//public class SoaNotificationService implements Scheduler {
//	
//	@Resource
//    private TimerService timerService;
//
//	private static Logger logger = Logger.getLogger(SoaNotificationService.class);	
//	
//	public static final String CRON_NAME = "Cron de Notificación de SOA";
//	public static final String JNDI_TIMER = "java:global/CustomerLogisticEAR/CustomerLogisticManager/SoaNotificationService!bbr.b2b.logistic.timers.managers.interfaces.Scheduler";
//	
//	@EJB
//	private SOANotificationManagerLocal notificationManagerLocal;
//		
//	@Timeout
//    public void scheduler(Timer timer) throws ServiceException{
//		logger.info("Se ejecuta " + CRON_NAME);
//    	
//		notificationManagerLocal.execute();	
//    }
//
//    @Override
//    public void initialize(String info, long initialinterval, long milliseconds) {
//    	logger.info("Creando Timer de SOANotification");
//    	// DETIENE TODOS LOS CRON ASOCIADOS A ESTE SERVICIO
//    	stop();
//    	
//    	// CREA EL TIMER
//    	logger.info("Creando Timer de SOANotification");
//  		timerService.createTimer(initialinterval, milliseconds, CRON_NAME);            
//    }
//
//    @Override
//    public void stop() {
//    	logger.info("Detiene algún " + CRON_NAME + " existente");
//        for (Timer timer : timerService.getTimers()) {
//        	logger.trace("Detiene timer: " + timer.getInfo());
//            timer.cancel();
//        }
//    }
//}