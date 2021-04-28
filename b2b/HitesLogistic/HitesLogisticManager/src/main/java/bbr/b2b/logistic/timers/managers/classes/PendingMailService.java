/*package bbr.b2b.logistic.timers.managers.classes;

import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.managers.interfaces.PendingMailManagerLocal;
import bbr.b2b.logistic.timers.managers.interfaces.Scheduler;

@Singleton
@DependsOn("managers/PendingMailManager")
public class PendingMailService implements Scheduler {
	
	@Resource
    private TimerService timerService;

	private static Logger logger = Logger.getLogger(PendingMailService.class);	
	
	public static final String CRON_NAME = "Cron de Envío de Correos";
	public static final String JNDI_TIMER = "java:global/HitesLogisticEAR/HitesLogisticManager/PendingMailService!bbr.b2b.logistic.timers.managers.interfaces.Scheduler";
	
	@EJB
	private PendingMailManagerLocal pendingmailmanager;
		
	@Timeout
    public void scheduler(Timer timer) throws ServiceException{
		logger.info("Se ejecuta " + CRON_NAME);
    	
		// VERIFICA QUE SE PUEDA EJECUTAR
		if (!LogisticConstants.getENABLE_SEND_MAIL_CRON()){
			return;
		}
    	
		pendingmailmanager.execute();	
    }

    @Override
    public void initialize(String info) {
        
    	// DETIENE TODOS LOS CRON ASOCIADOS A ESTE SERVICIO
    	stop();
    	
    	// CONFIGURAR PARAMETROS DE TIEMPO
    	// SE FIJA EL INICIO
    	long init_in_minutes = 1;
    	long init_in_milliseconds = init_in_minutes * 60 * 1000;
    	
    	// SE FIJA EL INTERVALO DE EJECUCION
    	long interval_in_minutes = 1;
    	long interval_in_milliseconds = interval_in_minutes * 60 * 1000;
    	
    	// CREA EL TIMER
  		timerService.createTimer(init_in_milliseconds, interval_in_milliseconds, CRON_NAME);            
    }

    @Override
    public void stop() {
    	logger.info("Detiene algún " + CRON_NAME + " existente");
        for (Timer timer : timerService.getTimers()) {
        	logger.trace("Detiene timer: " + timer.getInfo());
            timer.cancel();
        }
    }
}*/