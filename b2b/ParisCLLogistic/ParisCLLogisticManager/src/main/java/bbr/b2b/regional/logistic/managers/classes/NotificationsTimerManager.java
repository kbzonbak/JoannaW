package bbr.b2b.regional.logistic.managers.classes;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

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

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.managers.interfaces.NotificationsTimerManagerLocal;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactReportArrayDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactReportInitParam;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationDataDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.VendorNoticationDTO;
import bbr.b2b.regional.logistic.notifications.managers.interfaces.NotificationsReportManagerLocal;

@Stateless(name = "managers/NotificationsTimerManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationsTimerManager implements NotificationsTimerManagerLocal {

		private static Logger logger = Logger.getLogger(NotificationsTimerManager.class);
	
		private @Resource
		SessionContext ctx;

		private static boolean isServiceStarted = false;
		
		@EJB
		private NotificationsReportManagerLocal notificationsLocal;

		public synchronized void scheduleTimer(long initialinterval, long milliseconds) {
			// Cancelar todos los timers previamente asociados a este manager
			TimerService ts = ctx.getTimerService();
			Collection<Timer> timers = ts.getTimers();
			for (Iterator<Timer> iterator = timers.iterator(); iterator.hasNext();) {
				Timer timer = (Timer) iterator.next();
				timer.cancel();
			}
			// Crear el timerservice
			ctx.getTimerService().createTimer(initialinterval, milliseconds, "NotificationsTimerManager");
			isServiceStarted = true;
		}

		@Timeout
		public synchronized void timeoutHandler(Timer timer) {
			if (!isServiceStarted)
				return;

			Locale locale = new Locale("es", "CL");
			Calendar calendar = Calendar.getInstance(locale);
			Date date = calendar.getTime();
			String hourToDelete[] = null;
			try {
				hourToDelete = RegionalLogisticConstants.getInstance().getHourToNotificationsDetele().split(":");
			} catch (OperationFailedException e1) {
			}
			int hourdelete = Integer.parseInt(hourToDelete[0]);
			int minutesdelete = Integer.parseInt(hourToDelete[1]);
			if(calendar.get(Calendar.HOUR_OF_DAY) == hourdelete && calendar.get(Calendar.MINUTE) == minutesdelete){
				notificationsLocal.doDeleteOldNotificationData();
			}
			if(calendar.get(Calendar.MINUTE) == 00){
				VendorNoticationDTO[] vendors = notificationsLocal.getVendorsToNotification(date);
				if(vendors != null && vendors.length != 0)
				{
					for(int i = 0;i<vendors.length;i++){
						NotificationDataDTO[] vendordata = null;
						vendordata = notificationsLocal.getNotificationsDataByVendor(vendors[i].getRut(), date);
						
						if(vendordata == null || vendordata.length == 0){
							logger.info("Param:"+ vendors[i].getId() + " / "+ date);
							logger.info("Notificaciones Logistica - "+vendors[i].getName()+": No hay informacion hasta el momento");
							continue;
						}
						
						ContactReportInitParam initParam = new ContactReportInitParam();
						initParam.setVendorcode(vendors[i].getRut());
						initParam.setIsByFilter(false);
						ContactReportArrayDTO contacts = notificationsLocal.getContactsReport(initParam, false);
						

						if(contacts == null || contacts.getContacts().length == 0){
							System.out.println("Param:"+ vendors[i].getId());
							System.out.println("Notificaciones Logistica - "+vendors[i].getName()+": No hay contactos asignados");
							continue;
						}
						
						//send mail;
						String[] to = new String[contacts.getContacts().length];
						for(int j = 0;j<contacts.getContacts().length;j++){
							to[j] = contacts.getContacts()[j].getEmail();
						}
						String bcc[] = {"notificacionesb2b@bbr.cl"};
						String hour = calendar.get(Calendar.HOUR_OF_DAY)+":00";
						String subject = "B2B Paris: Notificaciones Logisticas";
						
						String body = "Estimado proveedor "+vendors[i].getTradename()+" ["+vendors[i].getRut()+"],<br><br>" +
								"Le informamos el resumen de sus notificaciones logísticas a las  "+hour+"<br><br>";
						body += "<table cellspacing='0' cellpadding='3' style='border:1px solid black; border-collapse:collapse; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>" +
								"<tr align='char'>" +
								"<th align='center' bgcolor='#999999' style='border:1px solid black;'><strong>Evento suscrito</strong></th>" +
								"<th align='center' bgcolor='#999999' style='border:1px solid black;'><strong>Cantidad</strong></th>" +
								"</tr>";
						for(int k=0;k<vendordata.length;k++){
							body += "<tr align='char'>" +
									"<td align='left' style='border:1px solid black;'>"+vendordata[k].getDescription()+"</td>" +
									"<td align='right' style='border:1px solid black;'>"+vendordata[k].getTotalload()+"</td>" +
									"</tr>";
						}
						body += "</table><br><br><b><em>Esto tiene finalidad de alertar el envío de OC de stock, pre-distribuidas y VEV (cualquiera sea el subtipo PD, CD, PD Courier). No sustituye la autogestión de revisar en B2B si tiene órdenes de Compras o Gestiones pendientes.</b></em>";
						body += "<br><br>Atte.<br>";
						body += "<br>B2B Paris.<br>";
						
						String session = "java:/Mail";
						String from = "soporteb2b@bbr.cl";
						try {
							Mailer.getInstance().sendMailBySession(to, null	, bcc, from, subject, body, true, null, session);
						} catch (OperationFailedException e) {
							System.out.println("No se pudo enviar correo de notificaciones logistica");
							e.printStackTrace();
						}
						System.out.println("Mail de notificaciones logistica enviado para "+vendors[i].getName());
					}
					
				}
				
			}

		}

}
