package bbr.b2b.regional.logistic.managers.classes;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import bbr.b2b.regional.logistic.managers.interfaces.AddChilexpressStatesTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.CheckBuyOrderStateManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.DeletePendingMailTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.DeletePendingMessageTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.KPIvevTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.LoadRecoveredMessagesManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.LogisticDatingDailyReportManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.MakeFillRateManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.MakeRankingManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.NotificationsTimerManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.PendingMailTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.PendingMessageTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.SOANotificationTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.TicketCourierTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.TicketTaxDocumentTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.TimerSetupLocal;
import bbr.b2b.regional.logistic.managers.interfaces.UploadChilexpressStatesFileTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.VeVNotificationTimerLocal;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;



@TransactionManagement(TransactionManagementType.BEAN)
@Singleton
public class TimerSetup implements TimerSetupLocal {


	private static Logger logger = Logger.getLogger(TimerSetup.class);

	private int start_hour;

	private int start_minutes;

	private int start_seconds;

	private int interval_in_minutes;

	@EJB
	private LoadRecoveredMessagesManagerLocal loadrecoveredmessagesmanager;

	@EJB
	private CheckBuyOrderStateManagerLocal checkbuyorderstatemanager;

	@EJB
	private MakeRankingManagerLocal makeRankingManager;

	@EJB
	private MakeFillRateManagerLocal makeFillRateManager;

	@EJB
	private LogisticDatingDailyReportManagerLocal logisticdatingdailyreport;

	@EJB
	private PendingMailTimerLocal pendingmailtimer;
	
	@EJB
	private PendingMessageTimerLocal pendingmessagetimer;

	@EJB
	private SOANotificationTimerLocal soanotificationtimer;
	
	@EJB
	private KPIvevTimerLocal kpivevtimer;
	
	@EJB
	private VeVNotificationTimerLocal vevnotificationtimer;
	
	@EJB
	private DeletePendingMailTimerLocal deletependingmailtimer;
	
	@EJB
	private DeletePendingMessageTimerLocal deletependingmessagetimer;
	
	@EJB
	private TicketTaxDocumentTimerLocal ticketTaxDocumentTimer;
	
	@EJB
	private TicketCourierTimerLocal ticketCourierTimer;
	
	@EJB
	private NotificationsTimerManagerLocal notificationsTimerLocal;
	
	@EJB
	private UploadChilexpressStatesFileTimerLocal uploadChilexpressStatesFileTimerLocal;
	
	@EJB
	private AddChilexpressStatesTimerLocal addChilexpressStatesTimerLocal;

	@Timeout
	public void timeout(Timer timer) {
		logger.info("TimerBean: timeout occurred");
	}
	
	private boolean isServiceStarted = false;
	
	@Schedule(minute="*/1", hour="*")
	public void doWork() throws Exception {
		
		//cron desactivado
		if(!Boolean.parseBoolean(B2BSystemPropertiesUtil.getProperty("EXECUTECRON"))) {
			logger.info("TimerSetup - CRONES DESACTIVADOS EN ESTE SERVER");
			return;
		}
		
		if(isServiceStarted)
			return;


		
		
		Calendar cal = Calendar.getInstance();

		// Tiempo Actual
		long now = cal.getTimeInMillis();

		// Inicio de Cron
		logger.info("TimerSetup - Starting");
		
		logger.info("Iniciando Cron de Procesamiento de Tickets de Facturación");
		ticketTaxDocumentTimer.scheduleTimer(60000L, 10 * 60 * 1000); // cada 10 minutos
		
		logger.info("Iniciando Cron de Procesamiento de Tickets Courier");
		ticketCourierTimer.scheduleTimer(60000L, 10 * 60 * 1000); // cada 10 minutos

		logger.info("Iniciando Cron de Recarga de Mensajes");
		loadrecoveredmessagesmanager.scheduleTimer(60000L, 300000L);
		
		logger.info("Iniciando Cron de Notificaciones Logísticas");
		notificationsTimerLocal.scheduleTimer(1000L, 60000L);
		
		logger.info("Iniciando Cron de Carga de Archivos de Estados Courier Chilexpress");
		uploadChilexpressStatesFileTimerLocal.scheduleTimer(1000L, Integer.parseInt(B2BSystemPropertiesUtil.getProperty("courierstatesfileinterval").trim()) * 60 * 1000L);
		
		logger.info("Iniciando Cron de Registro de Estados Courier Chilexpress");
		addChilexpressStatesTimerLocal.scheduleTimer(2000L, Integer.parseInt(B2BSystemPropertiesUtil.getProperty("courierstatesaddinterval").trim()) * 60 * 1000L);

		logger.info("Iniciando Cron de Vencimiento de Ordenes");
		// Fijando la hora de ejecución a las 1:00 am
		start_hour = 1;
		start_minutes = 0;
		start_seconds = 0;

		// Intervalo de la ejecución: 1440 = 24 horas
		interval_in_minutes = 1440;
		long intervalDuration = new Integer(interval_in_minutes).longValue() * 60 * 1000;

		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, start_hour);
		cal.set(Calendar.MINUTE, start_minutes);
		cal.set(Calendar.SECOND, start_seconds);

		long initTime = cal.getTimeInMillis();

		checkbuyorderstatemanager.scheduleTimer(initTime - now, intervalDuration);

		logger.info("Iniciando Cron de Ranking");
		makeRankingManager.scheduleTimer(initTime - now, 24 * 60 * 1000);// pregunta todos los dias dentro del manager

		logger.info("Iniciando Cron de FillRate");
		start_hour = 2;
		cal.set(Calendar.HOUR_OF_DAY, start_hour);
		initTime = cal.getTimeInMillis();
		makeFillRateManager.scheduleTimer(initTime - now, 24 * 60 * 1000);// pregunta todos los dias dentro del manager

		logger.info("Iniciando Cron de Generación de Reporte Diario de Citas");
		logisticdatingdailyreport.scheduleTimer(initTime - now + (1000 * 60 * 60), intervalDuration);

		// envío de notificaciones SOA
		// Intervalo configurable
		logger.info("Iniciando Cron de envío de Notificaciones SOA");
		Long intervalo = new Long(B2BSystemPropertiesUtil.getProperty("soatimeinterval"));
		intervalo = intervalo * 60 * 1000;
		soanotificationtimer.scheduleTimer(60000L, intervalo);

		// envío de mensajes al MQ
		logger.info("Iniciando Cron de envío de Mensajes al MQ");
		pendingmessagetimer.scheduleTimer(60000L, 2 * 60 * 1000); // cada 2 minutos
		
		// envío de correos
		logger.info("Iniciando Cron de envío de Correos");
		pendingmailtimer.scheduleTimer(60000L, 3 * 60 * 1000); // cada 3 minutos
		
		// Fijando la hora de ejecución a las 11:00 pm
		logger.info("Iniciando Cron de Limpieza de Mensajes Exitosos del MQ");
		start_hour = 23;
		start_minutes = 0;
		start_seconds = 0;
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, start_hour);
		cal.set(Calendar.MINUTE, start_minutes);
		cal.set(Calendar.SECOND, start_seconds);

		initTime = cal.getTimeInMillis();
		long diff = initTime - now;
		if(diff <= 0){
			cal.add(Calendar.DATE, 1);
			initTime = cal.getTimeInMillis();
		}

		// Intervalo de la ejecución: diario = 24 horas
		interval_in_minutes = 1440;
		intervalDuration = new Integer(interval_in_minutes).longValue() * 60 * 1000;

		deletependingmessagetimer.scheduleTimer(initTime - now, intervalDuration);
		
		// Fijando la hora de ejecución a las 11:30 pm
		logger.info("Iniciando Cron de Limpieza de Correos Exitosos");
		start_hour = 23;
		start_minutes = 30;
		start_seconds = 0;
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, start_hour);
		cal.set(Calendar.MINUTE, start_minutes);
		cal.set(Calendar.SECOND, start_seconds);

		initTime = cal.getTimeInMillis();
		diff = initTime - now;
		if(diff <= 0){
			cal.add(Calendar.DATE, 1);
			initTime = cal.getTimeInMillis();
		}
		
		deletependingmailtimer.scheduleTimer(initTime - now, intervalDuration);
		
		// Extracción de data para KPI
		cal.setTimeInMillis(now);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		initTime = cal.getTimeInMillis();
		logger.info("Iniciando Cron de Extracción de Datos para KPI");		
		kpivevtimer.scheduleTimer(initTime - now, 10 * 60 * 1000); // cada 10 minutos
		
		// Notificación VeV
		logger.info("Iniciando Cron de Notificación Resumen Primera Carga OC VeV");		
		vevnotificationtimer.scheduleTimer(initTime - now, 12 * 60 * 1000); // cada 12 minutos		
		
		
		isServiceStarted = true;
		
	}


}
