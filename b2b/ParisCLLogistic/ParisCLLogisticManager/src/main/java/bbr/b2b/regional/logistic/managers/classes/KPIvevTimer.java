package bbr.b2b.regional.logistic.managers.classes;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
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

import bbr.b2b.regional.logistic.kpi.classes.KPIvevExecutionStateServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevParameterServerLocal;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevExecutionStateW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevParameterW;
import bbr.b2b.regional.logistic.kpi.managers.interfaces.KPIReportManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.KPIvevTimerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.KPIvevTimerRemote;

@Stateless(name = "managers/KPIvevTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevTimer implements KPIvevTimerLocal, KPIvevTimerRemote{

	private static Logger logger = Logger.getLogger(KPIvevTimer.class);

	@EJB
	KPIvevParameterServerLocal kpivevparameterserverlocal;
	
	@EJB
	KPIvevExecutionStateServerLocal kpivevexecutionstateserverlocal;
	
	@EJB
	KPIReportManagerLocal kpimanagerlocal;	

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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron de calculo de KPI");

	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	@TransactionTimeout(value = 590)
	public synchronized void timeoutHandler(Timer timer) {

		// Par�metros KPI VeV
		HashMap<String, KPIvevParameterW> parameterMap = new HashMap<String, KPIvevParameterW>(); 
		try {
			KPIvevParameterW[] parameters = kpivevparameterserverlocal.getAllAsArray();
			for (KPIvevParameterW parameter : parameters) {
				parameterMap.put(parameter.getCode(), parameter);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Problemas al obtener par�metros KPI VeV");
		}		
		
		// KPI CD
		try {
			Calendar cal = Calendar.getInstance();
									
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int yearday = cal.get(Calendar.DAY_OF_YEAR);
			int hour1 = parameterMap.get("KPI_CD_SINCE").getValue().intValue();
			int hour2 = parameterMap.get("KPI_CD_UNTIL").getValue().intValue();
			
			if (hour >= hour1 && hour < hour2) {
				KPIvevExecutionStateW executionstate = kpivevexecutionstateserverlocal.getLastSuccessfulKPIvevExecution("CD");
				
				Calendar previousexec = Calendar.getInstance();
				if (executionstate != null) {
					previousexec.setTime(executionstate.getWhen1());
				}
				
				if (executionstate == null ||
						previousexec.get(Calendar.DAY_OF_YEAR) != yearday ||
							previousexec.get(Calendar.HOUR_OF_DAY) != hour) {
					// Fecha de b�squeda de información, que corresponder� al d�a actual de ejecución del proceso
					Calendar since = Calendar.getInstance();
					since.set(Calendar.DAY_OF_MONTH, 1);
					since.set(Calendar.HOUR_OF_DAY, 0);
					since.set(Calendar.MINUTE, 0);
					since.set(Calendar.SECOND, 0);
					since.set(Calendar.MILLISECOND, 0);
					
					Calendar until = Calendar.getInstance();
															
					kpimanagerlocal.doSetKPIvevCDData("-1", since.getTime(), until.getTime());
				}
				
				logger.info("Evento de KPI CD finalizado");
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento de KPI CD fall�");
		}
		
		// KPI PD
		try {
			Calendar cal = Calendar.getInstance();
			
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int yearday = cal.get(Calendar.DAY_OF_YEAR);
			int hour1 = parameterMap.get("KPI_PD_SINCE").getValue().intValue();
			int hour2 = parameterMap.get("KPI_PD_UNTIL").getValue().intValue();
			
			if (hour >= hour1 && hour < hour2) {
				KPIvevExecutionStateW executionstate = kpivevexecutionstateserverlocal.getLastSuccessfulKPIvevExecution("PD");
				
				Calendar previousexec = Calendar.getInstance();
				if (executionstate != null) {
					previousexec.setTime(executionstate.getWhen1());
				}
				
				if (executionstate == null ||
						previousexec.get(Calendar.DAY_OF_YEAR) != yearday ||
							previousexec.get(Calendar.HOUR_OF_DAY) != hour) {
					// Fecha de b�squeda de información, que corresponder� al d�a actual de ejecución del proceso
					Calendar since = Calendar.getInstance();
					since.set(Calendar.DAY_OF_MONTH, 1);
					since.set(Calendar.HOUR_OF_DAY, 0);
					since.set(Calendar.MINUTE, 0);
					since.set(Calendar.SECOND, 0);
					since.set(Calendar.MILLISECOND, 0);
					
					Calendar until = Calendar.getInstance();
										
					// Con courier
					kpimanagerlocal.doSetKPIvevPDData("-1", since.getTime(), until.getTime(), true);
					
					// Sin courier
					kpimanagerlocal.doSetKPIvevPDData("-1", since.getTime(), until.getTime(), false);
				}
				
				logger.info("Evento de KPI PD finalizado");
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento de KPI PD fall�");
		}
		
		// Cumplimientos de entrega
		try {
			Calendar cal = Calendar.getInstance();

			int yearday = cal.get(Calendar.DAY_OF_YEAR);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int hour1 = parameterMap.get("FINE_EXEC_HOUR").getValue().intValue();

			if (hour == hour1) {
				KPIvevExecutionStateW executionstate = kpivevexecutionstateserverlocal.getLastSuccessfulKPIvevExecution("CUMPLIMIENTOS");
				
				Calendar previousexec = Calendar.getInstance();
				if (executionstate != null) {
					previousexec.setTime(executionstate.getWhen1());
				}
				
				if (executionstate == null ||
						previousexec.get(Calendar.DAY_OF_YEAR) != yearday ||
							previousexec.get(Calendar.HOUR_OF_DAY) != hour) {
					kpimanagerlocal.doSetKPIvevComplianceData();
				}
			
				logger.info("Evento de Cumplimientos de entrega finalizado");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento de Cumplimientos de entrega fall�");
		}
	}
	
}