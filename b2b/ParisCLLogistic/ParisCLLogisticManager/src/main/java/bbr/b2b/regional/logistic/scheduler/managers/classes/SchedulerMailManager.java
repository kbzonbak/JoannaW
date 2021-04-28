package bbr.b2b.regional.logistic.scheduler.managers.classes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.scheduler.classes.PendingMailServerLocal;
import bbr.b2b.regional.logistic.scheduler.classes.PendingMailTypeServerLocal;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMailTypeW;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMailW;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerRemote;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Stateless(name = "managers/SchedulerMailManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SchedulerMailManager implements SchedulerMailManagerLocal, SchedulerMailManagerRemote {

	private static Logger logger = Logger.getLogger(SchedulerMailManager.class);

	private javax.ejb.SessionContext mySessionCtx;

	@EJB
	private PendingMailServerLocal pendingmailserverlocal;

	@EJB
	private PendingMailTypeServerLocal pendingmailtypeserverlocal;	

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	

	public PendingMailW doAddMailToQueue(String from, String[] toArr, String[] ccArr, String[] bccArr, String subject, String content, boolean isHtml, String mailsession, String type, String codemsge) throws OperationFailedException{
		
		PendingMailTypeW[] pmtype = null;
		try {
			pmtype = pendingmailtypeserverlocal.getByPropertyAsArray("code", type);
		} catch (NotFoundException e) {
			e.printStackTrace();
			pmtype = null;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		if (pmtype == null || pmtype.length == 0) {
			try {
				pmtype = pendingmailtypeserverlocal.getByPropertyAsArray("code", "DEFAULT");
			} catch (NotFoundException e) {
				throw new OperationFailedException("No se encuentra tipos de mensajes pendientes");
			}
		}
		
		// Setea Datos del Mail
		Date now = new Date();
		
		//Convirtiendo Array con destinatarios a String
		//TO
		String toStr = "";
		try{
			for (String to : toArr) {
				toStr += to + ",";
			}
		} catch(Exception e){
			e.printStackTrace();
		}
				
		//CC (opcional)
		String ccStr = "";
		try{
			for (String cc : ccArr) {
				ccStr += cc + ",";
			}
		} catch(Exception e){
			ccStr = "";
		}
		
		//BCC (opcional)
		String bccStr = "";
		try{
			for (String bcc : bccArr) {
				bccStr += bcc + ",";
			}
		}catch(Exception e){
			bccStr = "";
		}
		
		// Encoder
		BASE64Encoder encoder = new BASE64Encoder();
	
		PendingMailW pm = new PendingMailW();
		pm.setAttempts(0);
		pm.setCcmail(ccStr);
		pm.setCcomail(bccStr);
		pm.setCode(codemsge);
		pm.setMailsession(mailsession);
		try {
			pm.setContent(encoder.encode(content.getBytes("UTF8")));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			throw new OperationFailedException("No se puede ingresar Mail. Codificación incorrecta");
		}
		pm.setHtml(isHtml);
		pm.setFrommail(from);
		pm.setLastattempt(now);
		pm.setPendingmailtypeid(pmtype[0].getId());
		pm.setSubject(subject);
		pm.setTomail(toStr);
		pm.setStatus(0);
		pm.setWhen(now);

		try {
			pm = pendingmailserverlocal.addPendingMail(pm);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			throw new OperationFailedException("No se puede ingresar el mail");
		} catch (NotFoundException e) {
			e.printStackTrace();
			throw new OperationFailedException("No se puede ingresar el mail");
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationFailedException("No se puede ingresar el mail");
		}
		
		return pm;
	}

	public void doSendPendingMail(PendingMailW pendingmail) throws OperationFailedException {
		
		// Intentos de envio del correo
		pendingmail.setAttempts(pendingmail.getAttempts().intValue() + 1);
		pendingmail.setLastattempt(new Date());
		
		String msgtext = null;
		try{
			// Decoder
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes = decoder.decodeBuffer(pendingmail.getContent());
			msgtext = new String(bytes, "UTF8");
		} catch(IOException e){
			e.printStackTrace();
			throw new OperationFailedException("No fue posible obtener el mensaje del correo");
		}
		
		boolean isHtml = pendingmail.getHtml();
		
		// envío del mail
		Mailer mailer = Mailer.getInstance();
		
		String subject = pendingmail.getSubject();
		String mailSession = pendingmail.getMailsession();
		String mailsender = pendingmail.getFrommail();
		
		// Convierte string de input a array
		String[] toMailArr  = pendingmail.getTomail().split(",");
		String[] ccMailArr  = pendingmail.getCcmail().split(","); 
		String[] bccMAilArr = pendingmail.getCcomail().split(",");
		
		// JPE 20200122
		// Obtener posibles archivos adjuntos en la carpeta con nombre asociado al id
		File folder = new File(RegionalLogisticConstants.getInstance().getATTACHMENTS_PATH() + pendingmail.getId());
		List<File> attachments = null;
		if (folder.isDirectory()) {
			if (folder.listFiles() != null && folder.listFiles().length > 0) {
				attachments = Arrays.asList(folder.listFiles());				
			}
			else {
				logger.error("La carpeta de adjuntos asociada al id " + pendingmail.getId() + " est� vac�a");
			}
		}
		
		try {
			mailer.sendMailBySession(toMailArr, ccMailArr, bccMAilArr, mailsender, subject, msgtext, isHtml, attachments, mailSession);
			pendingmail.setStatus(1);

		} catch (Exception e) {
			e.printStackTrace();
			pendingmail.setStatus(0);
			logger.info("No fue posible enviar email");
		}
	
		try {
			pendingmailserverlocal.updateElement(pendingmail);
		} catch (NotFoundException e) {
			throw new OperationFailedException("No se puede actualizar el correo enviado");
		}
	}
	
	//Obtiene los correos pendientes de enviar
	public PendingMailW[] getPendingMailsToSend(int maxMailToSend) throws OperationFailedException, NotFoundException {
		PendingMailW[] result = pendingmailserverlocal.getByPropertyAsArray(1, maxMailToSend, "status", 0);
		return  result;
	}
	
	public int doDeletePendingMails() {
		
		String folderPath = null;		
		try {
			folderPath = RegionalLogisticConstants.getInstance().getATTACHMENTS_PATH();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		File folder = new File(folderPath);
		if (!folder.isDirectory()) {
			logger.error("No existe la carpeta de adjuntos de correos");
			return 0;
		}		
		
		Long[] pendingMailIds = pendingmailserverlocal.getPendingMailsToDelete();
		
		int count = 0;
		if (pendingMailIds != null && pendingMailIds.length > 0) {
			count = pendingmailserverlocal.doDeletePendingMails(pendingMailIds);
			
			// Eliminar todos los adjuntos y carpetas de estos mensajes
			for (Long id : pendingMailIds) {
				folder = new File(folderPath + id + "/");
				if (folder.exists()) {
					if (folder.isDirectory()) {
						if (folder.listFiles() != null && folder.listFiles().length > 0) {
							for (File file : folder.listFiles()) {
								file.delete();
							}
						}
					}
					
					folder.delete();
				}
			}
		}
		
		return count;
	}
		
}
