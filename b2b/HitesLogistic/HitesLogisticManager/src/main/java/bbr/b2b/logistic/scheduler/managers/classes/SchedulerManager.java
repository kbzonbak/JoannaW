package bbr.b2b.logistic.scheduler.managers.classes;

import java.nio.charset.Charset;
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
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerRemote;
import bbr.b2b.logistic.scheduler.classes.PendingMessageServerLocal;
import bbr.b2b.logistic.scheduler.classes.PendingMessageTypeServerLocal;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMessageTypeW;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMessageW;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.scheduler.report.classes.PendingMessageDTO;
import bbr.b2b.logistic.utils.QSender;

@Stateless(name = "managers/SchedulerManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SchedulerManager implements SchedulerManagerLocal, SchedulerManagerRemote {

	private static Logger logger = Logger.getLogger(SchedulerManager.class);

	@EJB
	private PendingMessageServerLocal pendingmessageserverlocal;

	@EJB
	private PendingMessageTypeServerLocal pendingmessagetypeserverlocal;

	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	
	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String headervalues, String content, LocalDateTime datetosend) throws OperationFailedException {
		doAddMessageQueue(factory, queue, type, codemsge, headervalues, content, datetosend, null);
	}

	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String headervalues, String content, LocalDateTime datetosend, Charset charset) throws OperationFailedException {
		
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
		
		LocalDateTime now = LocalDateTime.now();
		PendingMessageW pm = new PendingMessageW();
		pm.setAttempts(0);
		pm.setCode(codemsge);
		pm.setHeadervalues(new String(Base64.encodeBase64(headervalues.getBytes())));
		pm.setContent(new String(Base64.encodeBase64(content.getBytes())));
		pm.setLastattempt(now);
		pm.setPendingmessagetypeid(pmtype[0].getId());
		pm.setQueue(queue);
		pm.setFactory(factory);
		pm.setStatus(0); // no enviado
		pm.setDatatosend(datetosend);
		pm.setWhen(now);
		pm.setCharset("");
		if (charset != null)
			pm.setCharset(charset.name());

		
		try {
			pm = pendingmessageserverlocal.addPendingMessage(pm);
//			
//			// Si header value el vacio y la opcion de ingresar un headervalue a partir del ID es true,
//			// se ingresa usando el codigo + el id recien creado 
//			if(headervalues.equals("") && headervalueasID){
//				pendingmessageserverlocal.flush();
//				pendingmessageserverlocal.clear();
//				// Crea nuevo headervalue
//				String newheadervalue = pm.getCode() + "-" + pm.getId();
//				pm.setHeadervalues(new String(Base64.encodeBase64(newheadervalue.getBytes())));
//
//				pendingmessageserverlocal.updatePendingMessage(pm);
//			}

		} catch (AccessDeniedException e) {
			throw new OperationFailedException("No se puede ingresar el mensaje a planificar");
		} catch (NotFoundException e) {
			throw new OperationFailedException("No se puede ingresar el mensaje a planificar");
		}

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void doSendMessageQueue(PendingMessageW pm) throws OperationFailedException {

		PendingMessageTypeW[] pmtype = null;

		try {
			pmtype = pendingmessagetypeserverlocal.getByPropertyAsArray("id", pm.getPendingmessagetypeid());

		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationFailedException("No se pudo obtener el tipo de mensaje con id " + pm.getPendingmessagetypeid());
		}
		
		if (pmtype != null && pmtype.length > 0) {
			pm.setAttempts(pm.getAttempts().intValue() + 1);
			LocalDateTime now = LocalDateTime.now();
			pm.setLastattempt(now);
			
			String contentoutput = null;
			String[] headerparams = null;
			String[] headervalues = null;
			

			// Content
			contentoutput = new String(Base64.decodeBase64(pm.getContent().getBytes()));
			// HeaderValues
			headerparams = pmtype[0].getHeaderparameters() != null && !pmtype[0].getHeaderparameters().equals("") ? pmtype[0].getHeaderparameters().split("\\|") : null;
			headervalues = pm.getHeadervalues() != null && ! pm.getHeadervalues().equals("") ? new String(Base64.decodeBase64(pm.getHeadervalues().getBytes())).split("\\|") : null;
			
			// put del mensaje
			try{
				logger.info("Intentando enviar mensaje ID:" + pm.getId() + " codigo:" + pm.getCode() + " cola:" + pm.getQueue());
				if (pm.getCharset() == null || "".equals(pm.getCharset())){
					QSender.getInstance().putMessage(pm.getFactory(), pm.getQueue(), contentoutput, headerparams, headervalues);
				}
				else{
					QSender.getInstance().putMessage(pm.getFactory(), pm.getQueue(), contentoutput, headerparams, headervalues, Charset.forName(pm.getCharset()));
				}
				logger.info("Enviando mensaje ID:" + pm.getId() + " codigo:" + pm.getCode() + " cola:" + pm.getQueue());
				pm.setStatus(1); // mensaje enviado
				
			} catch (Exception ex) {
				pm.setStatus(0); // mensaje aun NO enviado
				logger.info("Error intentando enviar mensaje ID:" + pm.getId() + " codigo:" + pm.getCode() + " cola:" + pm.getQueue() + ", causa: "+ ex.getCause());
			}
		}
		
		try {
			pm = pendingmessageserverlocal.updatePendingMessage(pm);
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			// throw new OperationFailedException("No se puede actualizar el
			// mensaje enviado");
		}

	}

	public PendingMessageDTO[] getPendingMessagesToSend(int size) {
		PendingMessageDTO[] resultDTO = new PendingMessageDTO[0];
		try {
			resultDTO = pendingmessageserverlocal.getMessageToSend(size);
		} catch (OperationFailedException e) {
			e.printStackTrace();
			
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return resultDTO;
	}

	public int doDeletePendingMessage() {
		return pendingmessageserverlocal.doDeletePendingMessage();
	}

	
}
