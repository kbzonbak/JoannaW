package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.PendingMessageType;
import bbr.b2b.logistic.customer.data.wrappers.PendingMessageTypeW;

public interface IPendingMessageTypeServer extends IElementServer<PendingMessageType, PendingMessageTypeW> {

	PendingMessageTypeW addPendingMessageType(PendingMessageTypeW pendingmessagetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMessageTypeW[] getPendingMessageTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMessageTypeW updatePendingMessageType(PendingMessageTypeW pendingmessagetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
