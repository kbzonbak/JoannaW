package bbr.b2b.regional.logistic.managers.classes;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.couriers.classes.ChilexpressCourierStateTmpServerLocal;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.UploadChilexpressStatesFileTimerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;

@Stateless(name = "managers/UploadChilexpressStatesFileTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UploadChilexpressStatesFileTimer implements UploadChilexpressStatesFileTimerLocal {
	
	private static Logger logger = Logger.getLogger(UploadChilexpressStatesFileTimer.class);
	private static Logger chileexpressStateslog = Logger.getLogger("chilexpressStateslog");
	
	private static boolean isServiceStarted = false;
	
	@EJB
	ChilexpressCourierStateTmpServerLocal chilexpresscourierstatetmpserver;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;
	
	@EJB
	DODeliveryReportManagerLocal dodeliverymanager;
	
	@Resource
	private SessionContext ctx;

	public SessionContext getCtx() {
		return ctx;
	}
	
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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron para carga de archivos de estados Courier Chilexpress");
		isServiceStarted = true;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	@TransactionTimeout(value = 900)
	public synchronized void timeoutHandler(Timer timer) {
		
		if (!isServiceStarted) {
			return;
		}
		
		try {
			String dirr = RegionalLogisticConstants.getInstance().getUPLOADCHILEXPRESSSTATE();
			File[] files = new File(dirr).listFiles();
			if (files == null || files.length == 0) {
				return;
			}
			
			chileexpressStateslog.info("Iniciando la carga de archivos de estados Courier Chilexpress");
			
			// Crear comparador de archivos con la �ltima fecha de modificado
			Comparator fileComparator = new Comparator<File>() {
				public int compare(File o1, File o2) {
					return Long.compare(o1.lastModified(), o2.lastModified());
				}
		    };
		    
		    //Ordenar archivos por fecha de modificación
			Arrays.sort(files, fileComparator);
			
			List<String> errorList = new ArrayList<String>();
			for (File file : files) {
				if (!dodeliverymanager.doUploadChilexpressFile(file)) {
					errorList.add(file.getName());
				}
			}
			
			if (errorList.size() > 0) {
				String description = "Error procesando archivos!!!:<br>";
					for (String filename : errorList) {
						description += " - " + filename + "<br>";
					}								
					description += "Considerar que podr�a estar reintent�ndose su carga<br>";
				sendErrorMail("carga de estados Courier Chilexpress", description, "COURIER_STATES", "ERROR-UPLOAD-COURIER-STATES-CRON");
			}
			
			chileexpressStateslog.info("Terminada la carga de archivos de estados Courier Chilexpress");
			
		} catch (Exception e) {
			e.printStackTrace();
			chileexpressStateslog.info("Error en la carga de archivos de estados Courier Chilexpress");
			sendErrorMail("carga de estados Courier Chilexpress", "Error cargando archivos!!!", "COURIER_STATES", "ERROR-UPLOAD-COURIER-STATES-CRON");
		}
	}
	
	private void sendErrorMail(String subjectAppendix, String description, String type, String code) {
		
		try {
			// Enviar correo para notificar fallos
			String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String subject = "B2B Paris: Fallo en " + subjectAppendix;
			String from = RegionalLogisticConstants.getInstance().getMailSender();
			String[] to = RegionalLogisticConstants.getInstance().getERROR_MAIL_TO().split(",");
			String cc[] = null;
			String bcc[] = null;

			if (to != null && !to[0].equals("")) {
				DateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");

				Date now = new Date();

				String message =
					"<p>Ha ocurrido un fallo en " + subjectAppendix + " con fecha " + sdfDate.format(now) + ".<br>" + //
					"El detalle es el siguiente:</p>" + //
					"<p>" + description + "</p>" + //
					"<p>Atte.<br>" + //
					"B2B Paris</p>"; //

				schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, message, true, session, type, code);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No fue posible enviar email para notificar el fallo en " + subjectAppendix);
		}
	}	
}
