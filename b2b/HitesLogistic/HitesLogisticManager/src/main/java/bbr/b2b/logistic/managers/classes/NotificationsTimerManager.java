package bbr.b2b.logistic.managers.classes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.managers.interfaces.NotificationsTimerManagerLocal;
import bbr.b2b.logistic.notifications.classes.ContactServerLocal;
import bbr.b2b.logistic.notifications.data.wrappers.ContactW;
import bbr.b2b.logistic.notifications.managers.interfaces.NotificationsReportManagerLocal;
import bbr.b2b.logistic.notifications.report.classes.ContactNotificationDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationCSVDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationDataDTO;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterPOI;

@Stateless(name = "managers/NotificationsTimerManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationsTimerManager implements NotificationsTimerManagerLocal{

	private static Logger logger = Logger.getLogger(NotificationsTimerManager.class);
	
	private @Resource
	SessionContext ctx;

	@EJB
	private NotificationsReportManagerLocal notificationsLocal;
	
	@EJB
	private ContactServerLocal contactserver;

	public synchronized void doProcess() throws Exception {

		try {
			Locale locale = new Locale("es", "AR");
			Calendar calendar = Calendar.getInstance(locale);
			LocalDateTime date = LocalDateTime.now();
			String hourToDelete[] = null;
			try {
				hourToDelete = LogisticConstants.getHourToNotificationsDelete().split(":");
			} catch (OperationFailedException e1) {
			}
			int hourdelete = Integer.parseInt(hourToDelete[0]);
			int minutesdelete = Integer.parseInt(hourToDelete[1]);
			if (calendar.get(Calendar.HOUR_OF_DAY) == hourdelete && calendar.get(Calendar.MINUTE) == minutesdelete) {
				notificationsLocal.doDeleteOldNotificationData();
			}
			if (calendar.get(Calendar.MINUTE) == 0) {
				logger.info("Hora para chequear notificaciones");
				
				// Establece hora actual a minutos y segundos :00:00 
				date = date.withMinute(0).withSecond(0).withNano(0);
				ContactNotificationDTO[] contacts = notificationsLocal.getContactToNotification(date);
				if (contacts != null && contacts.length != 0) {
					logger.info("Se busca en los contactos las notificaciones");
					for (int i = 0; i < contacts.length; i++) {
						NotificationDataDTO[] contactdata = null;
						contactdata = notificationsLocal.getNotificationsDataByContact(contacts[i].getContactid(), date);
	
						if (contactdata == null || contactdata.length == 0) {
							logger.info("Param:" + contacts[i].getContactid() + " / " + date);
							logger.info("Notificaciones Logistica - " + contacts[i].getVendorname() + ": No hay informacion hasta el momento");
							continue;
						}
						
						ContactW contactW = null;
						try {
							contactW = contactserver.getById(contacts[i].getContactid());
						} catch (OperationFailedException | NotFoundException e3) {
							logger.error("No existe contacto con el ID: "+ contacts[i].getContactid());
							e3.printStackTrace();
							continue;
						}
	
						List<NotificationCSVDTO> datatoexcel = notificationsLocal.doExportCSVNotificationsData(contactW.getId(), contactW.getVendorid(), date);
	
						DataTable dt0 = new DataTable("notificaciones");
						DataColumn col01 = new DataColumn("Tipo", String.class);
						DataColumn col02 = new DataColumn("Numero de Identificacion", String.class);
						DataColumn col03 = new DataColumn("Fecha", String.class);
	
						dt0.addColumn(col01);
						dt0.addColumn(col02);
						dt0.addColumn(col03);
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
						DataRow row = null;
						for (NotificationCSVDTO excelOrder : datatoexcel) {
							row = dt0.newRow();
							row.setCellValue("Tipo", excelOrder.getEvent());
							row.setCellValue("Numero de Identificacion", excelOrder.getNumberid());
							row.setCellValue("Fecha", excelOrder.getWhen1().format(formatter));
							dt0.addRow(row);
						}
	
						XLSConverterPOI converter = new XLSConverterPOI();
						converter.setExcelheaderbold(true);
						converter.setShowexceltableborder(true);
						
						String realfilename = "DetalleNotificaciones";
						
						File xlsxFile = null;
						try {
							String filepath = LogisticConstants.getFILE_TRANSFER_PATH() + realfilename + System.currentTimeMillis() + ".xlsx";
							converter.ExportToXLSX(dt0, filepath, Charset.forName("UTF-16"));
							xlsxFile = new File(filepath);
						} catch (IOException e2) {
							e2.printStackTrace();
						} catch (OperationFailedException e3) {
							e3.printStackTrace();
						}
						logger.info(xlsxFile.getName());
						logger.info(xlsxFile.getAbsolutePath());
	
						// Enviar correo
						String[] to = {contactW.getEmail()};
						String hour = calendar.get(Calendar.HOUR_OF_DAY) + ":00";
						String subject = "B2B Hites: Notificaciones Logísticas";
	
						String body = "Estimado proveedor " + contacts[i].getVendorname() + " [" + contacts[i].getVendorcode() + "],<br><br>" +
								"Le informamos el resumen de sus notificaciones logísticas a las  " + hour + "<br><br>";
						body += "<table cellspacing='0' cellpadding='3' style='border:1px solid black; border-collapse:collapse; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>" +
								"<tr align='char'>" +
								"<th align='center' bgcolor='#999999' style='border:1px solid black;'><strong>Evento suscrito</strong></th>" +
								"<th align='center' bgcolor='#999999' style='border:1px solid black;'><strong>Cantidad</strong></th>" +
								"</tr>";
						for (int k = 0; k < contactdata.length; k++) {
							body += "<tr align='char'>" +
									"<td align='left' style='border:1px solid black;'>" + contactdata[k].getDescription() + "</td>" +
									"<td align='right' style='border:1px solid black;'>" + contactdata[k].getTotalload() + "</td>" +
									"</tr>";
						}
						body += "</table><br><br>Atte.<br>";
						body += "<br><br><b>B2B Hites</b>";
	
						String mailsender = null;
						String mailSession = null;
						try {
							mailsender = LogisticConstants.getMailSender();
							mailSession = LogisticConstants.getMAIL_SESSION();
						} catch (OperationFailedException e1) {
							e1.printStackTrace();
						}
						try {
							List<File> files = new ArrayList<File>();
							files.add(xlsxFile);
							Mailer.getInstance().sendMailBySession(to, null, null, mailsender, subject, body, true, files, mailSession);
						} catch (OperationFailedException e) {
							logger.error("No se pudo enviar correo de notificaciones logistica");
							e.printStackTrace();
						}
						logger.info("Mail de notificaciones logistica enviado para " + contacts[i].getVendorname());
					}	
				}	
			}
			logger.info("Notificaciones revisadas...");
			
		} catch (Exception e) {
			logger.error("DEMONIO_RECARGA: Error excepcional!");
			e.printStackTrace();
			throw e;
		}

	}
	
}