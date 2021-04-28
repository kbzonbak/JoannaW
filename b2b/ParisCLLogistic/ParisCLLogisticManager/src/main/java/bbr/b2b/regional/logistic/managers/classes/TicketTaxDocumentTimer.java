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
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.managers.interfaces.TicketTaxDocumentTimerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.DOTaxDocumentServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.DOTaxDocumentStateServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.DOTaxDocumentStateTypeServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.DOTaxDocumentTicketServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentStateTypeW;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentTicketW;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.DOTaxDocumentReportManagerLocal;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateCommentDTO;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.vendors.classes.PropertyVendorServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.PropertyVendorW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "managers/TicketTaxDocumentTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TicketTaxDocumentTimer implements TicketTaxDocumentTimerLocal {
	private static Logger logger = Logger.getLogger(TicketTaxDocumentTimer.class);

	private static boolean isServiceStarted = false;

	@EJB
	VendorServerLocal vendorServerLocal;
	
	@EJB
	DOTaxDocumentTicketServerLocal doTaxDocumentTicketServerLocal;
	
	@EJB
	DOTaxDocumentServerLocal doTaxDocumentServerLocal;
	
	@EJB
	DOTaxDocumentStateServerLocal doTaxDocumentStateServerLocal;
	
	@EJB
	DOTaxDocumentStateTypeServerLocal doTaxDocumentStateTypeServerLocal;
	
	@EJB
	DirectOrderServerLocal directOrderServerLocal;
	
	@EJB
	DOTaxDocumentReportManagerLocal doTaxDocumentReportManagerLocal;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;
	
	@EJB
	PropertyVendorServerLocal propertyvendorserver;

	@Resource
	private SessionContext ctx;
	
	public javax.ejb.SessionContext getSessionContext() {
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
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron procesamiento de tickets de Facturación ");

		isServiceStarted = true;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	@TransactionTimeout(value = 600)
	public synchronized void timeoutHandler(Timer timer) {
				
		logger.info("Evento autom�tico de procesamiento de tickets de Facturación iniciado");
		
		try {
			if (!isServiceStarted) {
				return;
			}
			
			int MAX_TAX_DOCUMENTS = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxtickettaxdocuments"));
			int counter = 0;
			
			// Obtener el estado de document tributario 'En Validación DTE', 'Facturada sin RM' y 'Documento Faltante'
			DOTaxDocumentStateTypeW dotdstValidating = doTaxDocumentStateTypeServerLocal.getByPropertyAsSingleResult("code", "DTE_VAL");
			DOTaxDocumentStateTypeW dotdstWRMInvoiced = doTaxDocumentStateTypeServerLocal.getByPropertyAsSingleResult("code", "W_RM_INV");
			DOTaxDocumentStateTypeW dotdstMissingDocument = doTaxDocumentStateTypeServerLocal.getByPropertyAsSingleResult("code", "DOC_FAL");
			HashMap<String, DOTaxDocumentStateTypeW> dotdstMap = new HashMap<String, DOTaxDocumentStateTypeW>();
			dotdstMap.put("DTE_VAL", dotdstValidating);
			dotdstMap.put("W_RM_INV", dotdstWRMInvoiced);
			dotdstMap.put("DOC_FAL", dotdstMissingDocument);
			
			// Obtener los tickets pendientes
			DOTaxDocumentTicketW[] tickets = doTaxDocumentTicketServerLocal.getPendingTickets(); 
			
			Date now = null;
			
			List<DirectOrderW> directorders = null;
			List<DOTaxDocumentStateCommentDTO> doTaxDocumentStateCommentList = null;
			HashMap<Long, DirectOrderW> doMap = new HashMap<Long, DirectOrderW>();
			HashMap<Long, String> vRUTMap = new HashMap<Long, String>();
			HashMap<Long, Integer> vInvoiceDaysMap = new HashMap<Long, Integer>();
			DOTaxDocumentW[] taxdocuments = null;
			PropertyVendorW[] pvInvoiceMaxPreviousDays = null;
			VendorW vendor = null;
			PropertyInfoDTO prop1 = null;
			PropertyInfoDTO prop2 = null;
			String vendorRUT = "";
			int invoiceMaxPreviousDays = 0;
						
			TICKETS:
			for (DOTaxDocumentTicketW ticket : tickets) {
				now = new Date();
				
				// Actualizar hora de ejecución del ticket (solo la primera vez)
				if (ticket.getStartdate() == null) {
					ticket.setStartdate(now);
					doTaxDocumentTicketServerLocal.updateDOTaxDocumentTicket(ticket);
				}
				
				// Obtener las órdenes asociadas al ticket y sus proveedores
				directorders = directOrderServerLocal.getByDOTaxDocumentTicket(ticket.getId());
				for (DirectOrderW directorder : directorders) {
					if (!doMap.containsKey(directorder.getId())) {
						doMap.put(directorder.getId(), directorder);
					}
										
					if (!vRUTMap.containsKey(directorder.getVendorid())) {
						vendor = vendorServerLocal.getById(directorder.getVendorid());
						vendorRUT = vendor.getRut() == null ? "0" : vendor.getRut();
						vRUTMap.put(vendor.getId(), vendorRUT);
						
						// Obtener el proveedor rollout para 'DIAS_FACT_VEVPD'
						prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
						prop2 = new PropertyInfoDTO("code", "code", "DIAS_FACT_VEVPD");
						pvInvoiceMaxPreviousDays = propertyvendorserver.getByPropertiesAsArray(prop1, prop2);
						invoiceMaxPreviousDays = Integer.valueOf(pvInvoiceMaxPreviousDays[0].getValue());
						vInvoiceDaysMap.put(vendor.getId(), invoiceMaxPreviousDays);
					}
				}			
				
				// Obtener las facturas asociadas al ticket en estado 'Procesando Documento'
				taxdocuments = doTaxDocumentServerLocal.getPendingDOTaxDocumentsByTicket(ticket.getId());
				
				boolean completeProcessing = true;
				for (DOTaxDocumentW taxdocument : taxdocuments) {
					if (++counter > MAX_TAX_DOCUMENTS) {
						if (!completeProcessing) {
							sendErrorMail("llamada de WS Paperless", "Error en llamada(s) a WS del ticket Paperless " + ticket.getTicketnumber() + "!!! Considerar que el ticket permanece abierto", "PAPERLESS_WS", "ERROR-PAPERLESS-WS");
						}
						break TICKETS;
					}
					
					// Analizar cada factura, validando su información contra sistema Paperless (PPL)
					completeProcessing &= doTaxDocumentReportManagerLocal.doPaperlessValidationWSByCron(taxdocument, doMap.get(taxdocument.getDirectorderid()), vRUTMap.get(doMap.get(taxdocument.getDirectorderid()).getVendorid()), vInvoiceDaysMap.get(doMap.get(taxdocument.getDirectorderid()).getVendorid()), ticket.getTicketnumber(), dotdstMap);
				}
				
				if (completeProcessing) {
					ticket.setEnddate(new Date());
					ticket.setProcessed(true);
					doTaxDocumentTicketServerLocal.updateDOTaxDocumentTicket(ticket);
					
					// Obtener los comentarios de la �ltima vez que pasaron por la validación
					// En este caso s� se analizan todos los documentos del ticket ya que se informar�n en el correo
					doTaxDocumentStateCommentList = doTaxDocumentStateServerLocal.getCommentsByDOTaxDocumentTicket(ticket.getId());
															
					// Enviar el correo
					sendMail(ticket.getTicketnumber(), ticket.getUsermail(), doTaxDocumentStateCommentList, "PAPERLESS_WS");
				}
				else {
					sendErrorMail("llamada de WS Paperless", "Error en llamada(s) a WS del ticket Paperless " + ticket.getTicketnumber() + "!!! Considerar que el ticket permanece abierto", "PAPERLESS_WS", "ERROR-PAPERLESS-WS");
				}
			}

			logger.info("Evento autom�tico de procesamiento de tickets de Facturación finalizado. " + --counter + " documentos procesados");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Evento autom�tico de procesamiento de tickets de Facturación fall�");
			sendErrorMail("procesamiento de tickets Paperless", "Error en cron de procesamiento de tickets Paperless!!! Considerar que los tickets podr�an acumularse sin procesar", "PAPERLESS_WS", "ERROR-PAPERLESS-CRON");
		}
	}
	
	public void sendMail(Long ticketnumber, String usermail, List<DOTaxDocumentStateCommentDTO> doTaxDocumentStateCommentList, String type) {
		
		try {
			String message =
				"<p>Estimado(a) usuario(a):</p>" +
				"<p>Comunicamos a usted el resultado del ticket N&deg; " + ticketnumber + " generado para el an&aacute;lisis de sus facturas.</p>";
			
			// JPE 20190110
			String tableData = "";
			boolean validating = false;
			for (DOTaxDocumentStateCommentDTO doTaxDocumentStateComment : doTaxDocumentStateCommentList) {
				tableData +=
						"<tr>" +
						"<td>" + doTaxDocumentStateComment.getDirectordernumber() + "</td>" +
						"<td>" + doTaxDocumentStateComment.getDotaxdocumentnumber() + "</td>" +
						"<td>" + (doTaxDocumentStateComment.getComment() != null ? doTaxDocumentStateComment.getComment() : "") + "</td>" +
						"<td>" + doTaxDocumentStateComment.getDotaxdocumentstatetype() + "</td>" +
						"</tr>";
				
				// Si al menos uno de los documentos est� en estado 'En Validación DTE'
				if (!validating && doTaxDocumentStateComment.getDotaxdocumentstatetypecode().equals("DTE_VAL")) {
					validating = true;
				}
			}
			
			if (validating) {
				message +=
					"<p>Se han detectado facturas que no cumplen con la normativa del SII y Cencosud, dado que la OC no se encuentra registrada " +
					"en el campo 801, ref 1 en su XML. Estas han quedado en estado 'En Validación DTE':</p>";
			}
			
			message +=
				"<p>&nbsp;</p>" +
				"<table cellspacing='0' cellpadding='3' style='border:1px solid black; border-collapse:collapse; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>" +
				"<tbody>" +
				"<tr>" +
				"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Orden de Compra</strong></center></td>" +
				"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Documento</strong></center></td>" +
				"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Resultado</strong></center></td>" +
				"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Estado</strong></center></td>" +
				"</tr>" +
					tableData +
				"</tbody>" +
				"</table>" +
				"<p>&nbsp;</p>" +
				"<p>Verifique el estado de carga de sus documentos en B2B.</p>" +
				"<p>&nbsp;</p>" +
				"<p><strong>No responder este correo dado que es generado de manera autom&aacute;tica por el sistema B2B.</strong></p>" +
				"<p>&nbsp;</p>" +
				"<p>Atte.</p>" +
				"<p>B2B Paris</p>";
			
			String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String subject = "B2B Paris: Carga de documentos tributarios Ticket " + ticketnumber;
			String from = RegionalLogisticConstants.getInstance().getMailSender();
			String[] to = {usermail};
			String cc[] = null;
			String bcc[] = null;
						
			schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, message, true, session, type, ticketnumber.toString());
									
		} catch (Exception e) {
			System.out.println("No se pudo enviar correo de Tickets de Facturación");
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
