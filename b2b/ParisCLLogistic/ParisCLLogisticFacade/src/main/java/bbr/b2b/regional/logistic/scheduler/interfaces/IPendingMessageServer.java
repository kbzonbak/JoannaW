package bbr.b2b.regional.logistic.scheduler.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMessageW;
import bbr.b2b.regional.logistic.scheduler.entities.PendingMessage;

public interface IPendingMessageServer extends IElementServer<PendingMessage, PendingMessageW> {

	public PendingMessageW addPendingMessage(PendingMessageW pendingmessage) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public PendingMessageW[] getPendingMessages() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public PendingMessageW updatePendingMessage(PendingMessageW pendingstate) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public PendingMessageW[] getMessageToSend(int size);
	
	public int doDeletePendingMessage();

}
