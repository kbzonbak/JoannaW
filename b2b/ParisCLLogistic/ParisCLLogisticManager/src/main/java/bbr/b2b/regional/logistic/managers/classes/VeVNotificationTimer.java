package bbr.b2b.regional.logistic.managers.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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

import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVVendorNotificationComparator;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVVendorNotificationDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.VeVNotificationTimerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;

@Stateless(name = "managers/VeVNotificationTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VeVNotificationTimer implements VeVNotificationTimerLocal {

	private static Logger logger = Logger.getLogger(VeVNotificationTimer.class);
	
	private static boolean summaryExecuted = false;

	@EJB
	VendorServerLocal vendorserverlocal;
	
	@EJB
	OrderServerLocal orderserverlocal;
	
	@EJB
	DirectOrderServerLocal directorderserverlocal;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;
	
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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron de Notificación Resumen Primera Carga OC VeV");

	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	public synchronized void timeoutHandler(Timer timer) {

		try {
			Calendar cal = Calendar.getInstance();
									
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int hour1 = RegionalLogisticConstants.getInstance().getVEV_SUMMARY_NOTIFICATION_BEGINS();
			int hour2 = RegionalLogisticConstants.getInstance().getVEV_SUMMARY_NOTIFICATION_ENDS();
			
			// Validar el horario de notificación
			if (hour >= hour1 && hour < hour2) {
				// Validar que el proceso no se haya ejecutado previamente en el d�a
				if (!summaryExecuted) {
					// Buscar los proveedores que hayan registrado el d�a anterior en el campo 'Primera VeV CD'
					cal.set(Calendar.HOUR, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					Date until = cal.getTime();
					
					cal.add(Calendar.DAY_OF_YEAR, -1);
					Date since = cal.getTime();
					
					Long[] vendorIds = vendorserverlocal.getFirstVeVCDVendorsByDate(since, until);
					
					// Obtener todas las OC VeV CD que se hayan cargado para esos proveedores en el d�a anterior
					List<VeVVendorNotificationDTO> nList = new ArrayList<VeVVendorNotificationDTO>();
					List<VeVVendorNotificationDTO> cdVeVNotifications = null;
					if (vendorIds != null && vendorIds.length > 0) {
						cdVeVNotifications = orderserverlocal.getOrdersByVendorsAndReleasedDate(vendorIds, since, until);
						nList.addAll(cdVeVNotifications);
					}
					
					// Buscar los proveedores que hayan registrado el d�a anterior en el campo 'Primera VeV PD'
					vendorIds = vendorserverlocal.getFirstVeVPDVendorsByDate(since, until);
					
					// Obtener todas las OC VeV PD que se hayan cargado para esos proveedores en el d�a anterior
					List<VeVVendorNotificationDTO> pdVeVNotifications = null;
					if (vendorIds != null && vendorIds.length > 0) {
						pdVeVNotifications = directorderserverlocal.getDirectOrdersByVendorsAndReleasedDate(vendorIds, since, until);
						nList.addAll(pdVeVNotifications);
					}
										
					// Validar que se haya encontrado información en al menos uno de los dos tipos VeV
					if (nList != null && nList.size() > 0) {
						// Ordenar por proveedor
						VeVVendorNotificationDTO[] notifications = nList.toArray(new VeVVendorNotificationDTO[nList.size()]);
						VeVVendorNotificationComparator comparator = new VeVVendorNotificationComparator("vendortradename", true);
						Arrays.sort(notifications, comparator);
												
						// Enviar correo para notificar a usuarios de Paris del resumen diario en la carga de nuevas OC VeV CD y PD
						DateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
						
						String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
						String subject = "B2B Paris: Resumen de primera carga OC VeV para proveedores en fecha " + sdfDate.format(since);
						String from = RegionalLogisticConstants.getInstance().getMailSender();
						String[] to = B2BSystemPropertiesUtil.getProperty("VEV_NOTIFICATION_MAIL").split(",");
						
						String cc[] = null;
						String bcc[] = null;

						if (to != null && !to[0].equals("")) {
							String message =
								"<p>Estimado(a) usuario(a):</p>" + //
								"<p>Comunicamos a usted el resumen con las primeras OC VeV cargadas para los siguientes proveedores:</p>" + //
								"<table cellspacing='0' cellpadding='5' style='border:1px solid black; border-collapse:collapse; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>" + //
									"<tbody>" + //
										"<tr>" + //
											"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Proveedor</strong></center></td>" + //
											"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>código Paris Proveedor</strong></center></td>" + //
											"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>N°mero de orden Comercial</strong></center></td>" + //
											"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>N°mero de Orden</strong></center></td>" + //
											"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Fecha envío</strong></center></td>" + //
											"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Fecha compromiso cliente</strong></center></td>" + //
											"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Sub tipo VeV</strong></center></td>" + //
										"</tr>"; //
							
							for (VeVVendorNotificationDTO notification : notifications) {
								message +=
									"<tr>" + //
										"<td style='border:1px solid black;'>" + notification.getVendortradename() + "</td>" + //
										"<td style='border:1px solid black;'>" + notification.getVendorcode() + "</td>" + //
										"<td style='border:1px solid black;'>" + notification.getOrdernumber() + "</td>" + //
										"<td style='border:1px solid black;'>" + notification.getRequestnumber() + "</td>" + //
										"<td style='border:1px solid black;'>" + sdfDate.format(notification.getEmitted()) + "</td>" + //
										"<td style='border:1px solid black;'>" + sdfDate.format(notification.getOriginaldeliverydate()) + "</td>" + //
										"<td style='border:1px solid black;'>" + notification.getVevtype() + "</td>" + //
									"</tr>"; //
							}
							
							message +=
									"</tbody>" + //
								"</table><br><br>" + //
								"<p><strong><i>Favor no responder este correo dado que es generado de manera autom�tica por el sistema B2B.</i></strong></p>" + //
								"<p>Atte.<br>" + //
								"B2B Paris</p>"; //
					
							schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, message, true, session, "NOTIF_VEV", "SUM-" + sdfDate.format(since));
						}				
					}
					
					summaryExecuted = true;
					logger.info("Evento de Notificación Resumen Primera Carga OC VeV termin�");
				}				
			}
			else if (hour < hour1) {
				summaryExecuted = false;
			}
					
		} catch (Exception e) {
			summaryExecuted = false;
			e.printStackTrace();
			logger.info("Evento de Notificación Resumen Primera Carga OC VeV fall�");
		}		
	}
	
}