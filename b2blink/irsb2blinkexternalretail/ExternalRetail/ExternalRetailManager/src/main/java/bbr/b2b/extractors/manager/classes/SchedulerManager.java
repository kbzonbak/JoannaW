package bbr.b2b.extractors.manager.classes;

import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerRemote;
import bbr.b2b.logistic.utils.QSender;

@Stateless(name = "managers/SchedulerManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SchedulerManager implements SchedulerManagerLocal, SchedulerManagerRemote {
	
	private static Logger logger = Logger.getLogger(SchedulerManager.class);
	
	@Resource
	private SessionContext mySessionCtx;

	public void setCtx(SessionContext mySessionCtx) {
		this.mySessionCtx = mySessionCtx;
	}
	
	public void doSendMessageQueue(String companyName, String accesscode, String code, String content, String charset) throws OperationFailedException {
		String queue = "jboss/activemq/queue/q_esbgrl";
		String factory = "jboss/qcf_soa";
		// put del mensaje
		try {
			if(charset == null || "".equals(charset))
				QSender.getInstance().putMessage(factory, queue, content);
			else
				QSender.getInstance().putMessage(factory, queue, content,  Charset.forName(charset));
			logger.info("Enviando mensaje Empresa: " + companyName + " Proveedor: " + accesscode + " code: " + code);
		} catch (Exception ex) {
			logger.error("Enviando mensaje Empresa:" + companyName + " Proveedor: " + accesscode + " code: " + code + " error: " + ex.getMessage());
		}
	}

	
	
}


