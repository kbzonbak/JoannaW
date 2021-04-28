package bbr.b2b.regional.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMessageW;

public interface ISchedulerManager {

	public PendingMessageW[] getPendingMessagesToSend(int size);

	public void doAddMessageQueue_newtx(String factory, String queue, String type, String codemsge, String content) throws OperationFailedException;
	
	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String content) throws OperationFailedException;
	
	public void doSendMessageQueue(PendingMessageW pm) throws OperationFailedException;
	
	public int doDeletePendingMessage();

	// public void doAddMail(String type, String content);

}
