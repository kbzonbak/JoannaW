package bbr.b2b.logistic.managers.interfaces;

import java.nio.charset.Charset;
import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.customer.data.wrappers.PendingMessageW;


public interface ISchedulerManager {

	public PendingMessageW[] getPendingMessagesToSend(int size);

	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String content) throws OperationFailedException;
	
	public void doAddMessageQueue(Date date, String factory, String queue, String type, String codemsge, String content) throws OperationFailedException;
	
	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String content, Charset charset) throws OperationFailedException;

	public void doAddMessageQueue(Date date, String factory, String queue, String type, String codemsge, String content, Charset charset) throws OperationFailedException;

	public void doSendMessageQueue(PendingMessageW pm) throws OperationFailedException;
	
	public int doDeletePendingMessage();

}
