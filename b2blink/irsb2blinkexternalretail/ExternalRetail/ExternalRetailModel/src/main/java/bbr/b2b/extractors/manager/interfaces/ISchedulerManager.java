package bbr.b2b.extractors.manager.interfaces;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

public interface ISchedulerManager {

	/*public PendingMessageW[] getPendingMessagesToSend(int size);

	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String content) throws OperationFailedException;

	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String content, Charset charset) throws OperationFailedException;

	public void doSendMessageQueue(PendingMessageW pm) throws OperationFailedException;*/
	
	public void doSendMessageQueue(String sitename, String accesscode, String code, String content, String charset) throws OperationFailedException;
	
	//public int doDeletePendingMessage();

}
