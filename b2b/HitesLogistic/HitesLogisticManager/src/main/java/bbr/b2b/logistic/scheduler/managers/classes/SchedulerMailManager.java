package bbr.b2b.logistic.scheduler.managers.classes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.logistic.managers.interfaces.SchedulerMailManagerRemote;
import bbr.b2b.logistic.scheduler.classes.PendingMailServerLocal;
import bbr.b2b.logistic.scheduler.classes.PendingMailTypeServerLocal;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMailTypeW;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMailW;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;


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
	
	@Resource
	public void setSessionContext(javax.ejb.SessionContext context) {
		this.mySessionCtx = context;
	}
	
	public void doAddMailToQueue(String from, String[] toArr, String[] ccArr, String[] bccArr, String subject,  String content, String mailsession, String type, String codemsge) throws OperationFailedException{
		
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
		LocalDateTime now = LocalDateTime.now();
		
		//Convirtiendo Array con destinatarios a String
		//TO
		String toStr = "";
		try{
			toStr = String.join(", ", toArr);	
		} catch(Exception e){
			e.printStackTrace();
		}
		
		//CC (opcional)
		String ccStr = "";
		try{
			ccStr = String.join(", ", ccArr);	
		} catch(Exception e){
			ccStr = "";
		}
		
		//BCC (opcional)
		String bccStr = "";
		try{
			bccStr = String.join(", ", bccArr);	
		}catch(Exception e){
			bccStr = "";
		}
		
		// Encoder
		PendingMailW pm = new PendingMailW();
		pm.setAttempts(0);
		pm.setCcmail(ccStr);
		pm.setCcomail(bccStr);
		pm.setCode(codemsge);
		pm.setMailsession(mailsession);
		
		try {
			pm.setContent(Base64.encodeBase64String(content.getBytes("UTF8")));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			throw new OperationFailedException("No se puede ingresar Mial. Codificacion incorrecta");
		}
		pm.setFrommail(from);
		pm.setLastattempt(now);
		pm.setPendingmailtypeid(pmtype[0].getId());
		pm.setSubject(subject);
		pm.setTomail(toStr);
		pm.setStatus(0);
		pm.setWhen(now);

		try {
			pendingmailserverlocal.addPendingMail(pm);
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
	}

	public void doSendPendingMail(PendingMailW pendingmail) throws OperationFailedException {
		
		// Intentos de envio del correo
		pendingmail.setAttempts(pendingmail.getAttempts().intValue() + 1);
		pendingmail.setLastattempt(LocalDateTime.now());
		
		String msgtext = null;
		try{
			// Decoder
			byte[] bytes = Base64.decodeBase64(pendingmail.getContent());
			msgtext = new String(bytes, "UTF8");
		} catch(IOException e){
			e.printStackTrace();
			throw new OperationFailedException("No fue posible obtener el mensaje del correo");
		}
		
		// Env�o del mail
		Mailer mailer = Mailer.getInstance();
		
		String subject = pendingmail.getSubject();
		String mailSession = pendingmail.getMailsession();
		String mailsender = pendingmail.getFrommail();
		
		// Convierte string de input a array
		String[] toMailArr  = pendingmail.getTomail().split(",");
		String[] ccMailArr  = pendingmail.getCcmail().split(","); 
		String[] bccMAilArr = pendingmail.getCcomail().split(",");
		
		try {
			mailer.sendMailBySession(toMailArr, ccMailArr, bccMAilArr, mailsender, subject, msgtext, true, null, mailSession);
			pendingmail.setStatus(1);

		} catch (Exception e) {
//			e.printStackTrace();
			pendingmail.setStatus(0);
			logger.info("No fue posible enviar email");
		}
	
		try {
			try {
				pendingmailserverlocal.updatePendingMail(pendingmail);
			} catch (AccessDeniedException e) {
				
				e.printStackTrace();
			}
		} catch (NotFoundException e) {
			throw new OperationFailedException("No se puede actualizar el correo enviado");
		}
	}
	
	
	//Obtiene los correos pendientes de enviar
	public PendingMailW[] getPendingMailToSend(int maxMailToSend) throws OperationFailedException, NotFoundException {
		PendingMailW[] result = pendingmailserverlocal.getByPropertyAsArray(1, maxMailToSend, "status", 0);
		return  result;
				
	}
	
}
