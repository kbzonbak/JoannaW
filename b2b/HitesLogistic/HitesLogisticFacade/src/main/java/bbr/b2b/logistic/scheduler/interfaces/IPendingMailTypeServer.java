package bbr.b2b.logistic.scheduler.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.scheduler.entities.PendingMailType;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMailTypeW;

public interface IPendingMailTypeServer extends IElementServer<PendingMailType, PendingMailTypeW> {

	PendingMailTypeW addPendingMailType(PendingMailTypeW pendingmailtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMailTypeW[] getPendingMailTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMailTypeW updatePendingMailType(PendingMailTypeW pendingmailtype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
