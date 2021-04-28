package bbr.b2b.logistic.timers.managers.classes;

/*import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

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
import bbr.b2b.logistic.managers.interfaces.CheckBuyOrderStateManagerLocal;
import bbr.b2b.logistic.timers.managers.interfaces.Scheduler;

@Singleton
@DependsOn("managers/CheckBuyOrderStateManager")
public class CheckBuyOrderStateService implements Scheduler {

	@Resource
    private TimerService timerService;

	private static Logger logger = Logger.getLogger(CheckBuyOrderStateService.class);	
	
	public static final String CRON_NAME = "Cron de Vencimiento de Ordenes";
	public static final String JNDI_TIMER = "java:global/HitesLogisticEAR/HitesLogisticManager/CheckBuyOrderStateService!bbr.b2b.logistic.timers.managers.interfaces.Scheduler";
	
	
	@EJB
	private CheckBuyOrderStateManagerLocal checkbuyorderstatemanager;
	
	@Timeout
    public void scheduler(Timer timer) throws ServiceException{
		logger.info("Se ejecuta " + CRON_NAME);
    	
		// VERIFICA QUE SE PUEDA EJECUTAR
		if(! LogisticConstants.getENABLE_CHECKBUYORDER()) {	
			return;
		}
    	
		checkbuyorderstatemanager.execute();	
    }
	
    @Override
    public void initialize(String info) {

    	// DETIENE TODOS LOS CRON ASOCIADOS A ESTE SERVICIO
    	stop();    	
    	
    	// SE CONFIGURAN PARAMETROS DE TIEMPO        
        // SE FIJA LA HORA DE INICIO
    	int start_hour = LogisticConstants.getTIME_CHECKBUYORDER();
        
        // SE FIJA EL INTERVALO DE EJECUCION
		long interval_in_hours = 24;
		long interval_in_milliseconds = interval_in_hours * 60 * 60 * 1000;
    	
    	// SE FIJA EL INSTANTE EN QUE SE EJECUTARA EL CRON
    	LocalDateTime initLocalTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withHour(start_hour).withMinute(0).withSecond(0);
		//Instant initInstant = initLocalTime.atZone(ZoneId.systemDefault()).toInstant();
		
		// SE OBTIENE EL INSTANTE ACTUAL
		LocalDateTime nowLocalTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		//Instant nowInstant = nowLocalTime.atZone(ZoneId.systemDefault()).toInstant();
    	
		Duration duration = Duration.between(nowLocalTime, initLocalTime);
		long diff = duration.getSeconds();
		
		if(diff <= 0) {
			initLocalTime = initLocalTime.plusDays(1);
		}
		
		Instant initInstant = initLocalTime.atZone(ZoneId.systemDefault()).toInstant();
		Instant nowInstant = nowLocalTime.atZone(ZoneId.systemDefault()).toInstant();
		
		
 		// CREA EL TIMER
 		timerService.createTimer(initInstant.toEpochMilli() - nowInstant.toEpochMilli(), interval_in_milliseconds, CRON_NAME);       
    }

    @Override
    public void stop() {
    	logger.info("Detiene algún " + CRON_NAME + " existente");
        for (Timer timer : timerService.getTimers()) {
        	logger.trace("Detiene timer: " + timer.getInfo());
            timer.cancel();            
        }
    }
}
*/