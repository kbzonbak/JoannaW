package bbr.b2b.regional.logistic.managers.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.couriers.classes.CourierServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierTicketDetailServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierTicketServerLocal;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketDetailW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierW;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierTicketMailInfoBean;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.TicketCourierTimerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;

@Stateless(name = "managers/TicketCourierTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TicketCourierTimer implements TicketCourierTimerLocal {
	
	private static Logger logger = Logger.getLogger(TicketCourierTimer.class);

	private static boolean isServiceStarted = false;

	@EJB
	DODeliveryStateTypeServerLocal dodeliverystatetypeserver;
	
	@EJB
	CourierTicketServerLocal courierticketserver;

	@EJB
	CourierTicketDetailServerLocal courierticketdetailserver;

	@EJB
	CourierServerLocal courierserver;
	
	@EJB
	DODeliveryReportManagerLocal dodeliverymanager;
	
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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron procesamiento de tickets Couriers");

		isServiceStarted = true;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	@TransactionTimeout(value = 600)
	public synchronized void timeoutHandler(Timer timer) {
		
		logger.info("Evento autom�tico de procesamiento de tickets couriers Iniciado");
		
		try {
			if (!isServiceStarted) {
				return;
			}

			int MAX_COURIER_TICKET_DETAILS = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxcourierticketdetails"));
			int counter = 0;

			CourierTicketW[] couriertikets = courierticketserver.getPendingTickets();

			HashMap<String, DODeliveryStateTypeW> dodstMap = new HashMap<String, DODeliveryStateTypeW>();
			dodstMap.put("ETIQ_POR_SOLICITAR", dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_POR_SOLICITAR"));
			dodstMap.put("ETIQ_SOLICITADA", dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_SOLICITADA"));
			dodstMap.put("PEND_AGENDAR", dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_AGENDAR"));			
			
			PropertyInfoDTO prop1 = null;
			PropertyInfoDTO prop2 = null;
			Date now = null;
						
			TICKETS:
			for (CourierTicketW courierticket : couriertikets) {
				now = new Date();
				
				// Actualizar hora de ejecución del ticket (solo la primera vez)
				if (courierticket.getStartdate() == null) {
					courierticket.setStartdate(now);
					courierticketserver.updateCourierTicket(courierticket);
				}
								
				CourierW courier = courierserver.getById(courierticket.getCourierid());
				prop1 = new PropertyInfoDTO("courierticket.id", "courierticketid", courierticket.getId());
				prop2 = new PropertyInfoDTO("processed", "processed", false);
				List<CourierTicketDetailW> courierticketdetails = courierticketdetailserver.getByProperties(prop1, prop2);
				
				boolean completeProcessing = true;
				for (CourierTicketDetailW courierTicketDetail : courierticketdetails) {
					if (++counter > MAX_COURIER_TICKET_DETAILS) {
						if (!completeProcessing) {
							sendErrorMail("llamada de WS Courier", "Error en llamada(s) a WS del ticket Courier " + courierticket.getTicketnumber() + "!!! Considerar que el ticket permanece abierto", "COURIER_WS", "ERROR-COURIER-WS");
						}
						break TICKETS;
					}
					
					completeProcessing &= dodeliverymanager.doCourierTicketDetailWS(courierticket.getTicketnumber(), courier, courierTicketDetail, dodstMap);
				}
				
				if (completeProcessing) {
					List<CourierTicketMailInfoBean> mailinfolist = courierticketdetailserver.getMailInfoByCourierTicket(courierticket.getId());
					
					courierticket.setProcessed(true);
					courierticket.setEnddate(new Date());
					courierticketserver.updateCourierTicket(courierticket);
					
					sendMail(courierticket.getTicketnumber(), courier.getDescription(), courierticket.getUsermail(), mailinfolist, "COURIER_WS");
				}
				else {
					sendErrorMail("llamada de WS Courier", "Error en llamada(s) a WS del ticket Courier " + courierticket.getTicketnumber() + "!!! Considerar que el ticket permanece abierto", "COURIER_WS", "ERROR-COURIER-WS");
				}
			}

			logger.info("Evento autom�tico de procesamiento de tickets couriers finalizado. " + --counter + " Detalles de Tickets procesados");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento autom�tico de procesamiento de tickets couriers fall�");
			sendErrorMail("procesamiento de tickets Courier", "Error en cron de procesamiento de tickets Courier!!! Considerar que los tickets podr�an acumularse sin procesar", "COURIER_WS", "ERROR-COURIER-CRON");
		}
	}

	public void sendMail(Long ticketnumber, String couriername, String usermail, List<CourierTicketMailInfoBean> mailinfo, String type) {
		
		try {
			String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String subject = "B2B Paris: Carga de Ticket Courier " + ticketnumber;
			String from = RegionalLogisticConstants.getInstance().getMailSender();
			String[] to = {usermail};
			String cc[] = null;
			String bcc[] = null;			

			String message =
				"<p>Estimado(a) usuario(a):</p>" + "<p>Comunicamos a usted el resultado del ticket N&deg; " + ticketnumber + " " +
				"generado para la emisión de documentos a courier " + couriername + "</p>" + "<p>&nbsp;</p>" +
				"<table cellspacing='0' cellpadding='3' style='border:1px solid black; border-collapse:collapse; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>" + "<tbody>" + "<tr>" +
				"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Orden de Compra</strong></center></td>" + "<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Despacho</strong></center></td>" +
				"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Resultado</strong></center></td>" + "</tr>";
			
			for (CourierTicketMailInfoBean infobean : mailinfo) {
				message += "<tr>" + "<td >" + infobean.getDirectordernumber() + "</td>" + "<td >" + infobean.getDodeliverynumber() + "</td>" + "<td >" + infobean.getCourierticketdetailresult() + "</td>" + "</tr>";
			}
			
			message +=
				"</tbody>" + "</table>" + "<p>&nbsp;</p>" + "<p>Verifique en B2B las etiquetas generadas para aquellos despachos exitosos.</p>" +
				"<p>&nbsp;</p>" +
				"<p><strong>No responder este correo dado que es generado de manera autom&aacute;tica por el sistema B2B.</strong></p>" +
				"<p>&nbsp;</p>" + "<p>Atte.</p>" + "<p>B2B Paris</p>";

			schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, message, true, session, type, ticketnumber.toString());

		} catch (Exception e) {
			logger.error("No se pudo enviar correo de Ticket Courier " + ticketnumber);
			e.printStackTrace();
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
