package bbr.b2b.regional.logistic.scheduler.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMessageTypeW;
import bbr.b2b.regional.logistic.scheduler.entities.PendingMessageType;

public interface IPendingMessageTypeServer extends IElementServer<PendingMessageType, PendingMessageTypeW> {

	public PendingMessageTypeW addPendingMessageType(PendingMessageTypeW pendingstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public PendingMessageTypeW[] getPendingMessageTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public PendingMessageTypeW updatePendingMessageType(PendingMessageTypeW pendingstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
