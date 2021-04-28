package bbr.b2b.regional.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMailW;

public interface ISchedulerMailManager {

	public PendingMailW doAddMailToQueue(String from, String[] to, String[] cc, String[] cco, String subject, String content, boolean isHtml, String mailsession, String type, String codemsge) throws OperationFailedException;
	public void doSendPendingMail(PendingMailW pendingmail)  throws OperationFailedException;
	public PendingMailW[] getPendingMailsToSend(int maxMailToSend) throws OperationFailedException, NotFoundException;
	public int doDeletePendingMails();
	
}
