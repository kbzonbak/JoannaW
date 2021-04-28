package bbr.b2b.logistic.managers.classes;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.customer.classes.PendingMessageServerLocal;
import bbr.b2b.logistic.customer.classes.PendingMessageTypeServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.PendingMessageTypeW;
import bbr.b2b.logistic.customer.data.wrappers.PendingMessageW;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerRemote;
import bbr.b2b.logistic.utils.QSender;

@Stateless(name = "managers/SchedulerManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SchedulerManager implements SchedulerManagerLocal, SchedulerManagerRemote {
	
	private static Logger logger = Logger.getLogger(SchedulerManager.class);
	
	@Resource
	private SessionContext mySessionCtx;

	@EJB
	private PendingMessageServerLocal pendingmessageserverlocal;

	@EJB
	private PendingMessageTypeServerLocal pendingmessagetypeserverlocal;
	
//	@Resource
//	public javax.ejb.SessionContext getSessionContext() {
//		return mySessionCtx;
//	}
	
	public void setCtx(SessionContext mySessionCtx) {
		this.mySessionCtx = mySessionCtx;
	}
	
	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String content) throws OperationFailedException {
		doAddMessageQueue(new Date(), factory, queue, type, codemsge, content, null);
	}
	
	public void doAddMessageQueue(Date date, String factory, String queue, String type, String codemsge, String content) throws OperationFailedException {
		doAddMessageQueue(date, factory, queue, type, codemsge, content, null);
	}
	
	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String content, Charset charset) throws OperationFailedException {
		doAddMessageQueue(new Date(), factory, queue, type, codemsge, content, charset);
	}

	public void doAddMessageQueue(Date date, String factory, String queue, String type, String codemsge, String content, Charset charset) throws OperationFailedException {
		PendingMessageTypeW[] pmtype = null;
		try {
			pmtype = pendingmessagetypeserverlocal.getByPropertyAsArray("code", type);
		} catch (NotFoundException e) {
			e.printStackTrace();
			pmtype = null;
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		if (pmtype == null || pmtype.length == 0) {
			try {
				pmtype = pendingmessagetypeserverlocal.getByPropertyAsArray("code", "DEFAULT");
			} catch (NotFoundException e) {
				throw new OperationFailedException("No se encuentra tipos de mensajes pendientes");
			}
		}

		PendingMessageW pm = new PendingMessageW();
		pm.setAttempts(0);
		pm.setCode(codemsge);
		pm.setContent(new String(Base64.encodeBase64(content.getBytes())));
		pm.setLastattempt(new Date());
		pm.setPendingmessagetypeid(pmtype[0].getId());
		pm.setQueue(queue);
		pm.setFactory(factory);
		pm.setStatus(0); // no enviado
		pm.setWhen(date);
		pm.setCharset("");
		if (charset != null)
			pm.setCharset(charset.name());

		try {
			pendingmessageserverlocal.addPendingMessage(pm);
		} catch (AccessDeniedException e) {
			throw new OperationFailedException("No se puede ingresar el mensaje a planificar");
		} catch (NotFoundException e) {
			throw new OperationFailedException("No se puede ingresar el mensaje a planificar");
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void doSendMessageQueue(PendingMessageW pm) throws OperationFailedException {

		pm.setAttempts(pm.getAttempts().intValue() + 1);
		pm.setLastattempt(new Date());

		String contenttoput = new String(Base64.decodeBase64(pm.getContent().getBytes()));
		// put del mensaje
		try {
			if(pm.getCharset() == null || "".equals(pm.getCharset()))
				QSender.getInstance().putMessage(pm.getFactory(), pm.getQueue(), contenttoput);
			else
				QSender.getInstance().putMessage(pm.getFactory(), pm.getQueue(), contenttoput,  Charset.forName(pm.getCharset()));
			logger.info("Enviando mensaje ID:" + pm.getId() + " codigo:" + pm.getCode() + " cola:" + pm.getQueue());
			pm.setStatus(1); // mensaje enviado
		} catch (Exception ex) {
			pm.setStatus(0); // mensaje aun NO enviado
		}
		// fin put

		try {
			pm = pendingmessageserverlocal.updatePendingMessage(pm);
		} catch (NotFoundException e) {
			throw new OperationFailedException("No se puede actualizar el mensaje enviado");
		} catch (AccessDeniedException e) {
			throw new OperationFailedException("No se puede actualizar el mensaje enviado");
		}

	}

	public PendingMessageW[] getPendingMessagesToSend(int size) {
		PendingMessageW[] result = new PendingMessageW[0];
		try {
			result = pendingmessageserverlocal.getMessageToSend(size);
		} catch (OperationFailedException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int doDeletePendingMessage() {
		return pendingmessageserverlocal.doDeletePendingMessage();
	}
	
}


