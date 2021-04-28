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
//import bbr.b2b.logistic.managers.interfaces.PendingMessageManagerLocal;
//import bbr.b2b.logistic.timers.managers.interfaces.Scheduler;
//
//@Singleton
//@DependsOn("managers/PendingMessageManager")
//public class PendingMessageService implements Scheduler {
//	
//	@Resource
//    private TimerService timerService;
//
//	private static Logger logger = Logger.getLogger(PendingMessageService.class);	
//	
//	public static final String CRON_NAME = "Cron de envío de mensajes pendientes";
//	public static final String JNDI_TIMER = "java:global/CustomerLogisticEAR/CustomerLogisticManager/PendingMessageService";
//	//!bbr.b2b.logistic.timers.managers.interfaces.Scheduler
//	
//	@EJB
//	private PendingMessageManagerLocal pendingMessageManager;
//		
//	@Timeout
//    public void scheduler(Timer timer) throws ServiceException{
//		logger.info("Se ejecuta " + CRON_NAME);
//    	
//		pendingMessageManager.execute();	
//    }
//
//    @Override
//    public void initialize(String info, long initialinterval, long milliseconds) {
//    	// DETIENE TODOS LOS CRON ASOCIADOS A ESTE SERVICIO
//    	stop();
//    	
//    	// CREA EL TIMER
//    	logger.info("Creando Timer de PendingMessages");
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