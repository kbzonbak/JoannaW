package bbr.b2b.logistic.managers.interfaces;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMessageW;
import bbr.b2b.logistic.scheduler.report.classes.PendingMessageDTO;


public interface ISchedulerManager {

	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String headervalues, String content, LocalDateTime datetosend) throws OperationFailedException;
	public void doAddMessageQueue(String factory, String queue, String type, String codemsge, String headervalues, String content, LocalDateTime datetosend, Charset charset) throws OperationFailedException;
	public void doSendMessageQueue(PendingMessageW pm) throws OperationFailedException;
	public PendingMessageDTO[] getPendingMessagesToSend(int size);
	public int doDeletePendingMessage();

}
