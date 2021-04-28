package bbr.b2b.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMailW;

public interface ISchedulerMailManager {

	public void doAddMailToQueue(String from, String[] to, String[] cc, String[] cco, String subject,  String content, String mailsession, String type, String codemsge) throws OperationFailedException;
	public void doSendPendingMail(PendingMailW pendingmail)  throws OperationFailedException;
	public PendingMailW[] getPendingMailToSend(int maxMailToSend) throws OperationFailedException, NotFoundException;


}
