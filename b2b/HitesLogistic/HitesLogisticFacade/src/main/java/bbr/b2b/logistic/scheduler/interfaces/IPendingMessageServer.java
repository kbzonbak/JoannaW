package bbr.b2b.logistic.scheduler.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.scheduler.entities.PendingMessage;
import bbr.b2b.logistic.scheduler.report.classes.PendingMessageDTO;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMessageW;

public interface IPendingMessageServer extends IElementServer<PendingMessage, PendingMessageW> {

	PendingMessageW addPendingMessage(PendingMessageW pendingmessage) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMessageW[] getPendingMessages() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMessageW updatePendingMessage(PendingMessageW pendingmessage) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMessageDTO[] getMessageToSend(int size) throws OperationFailedException, NotFoundException;
	int doDeletePendingMessage(); 
}
