package bbr.b2b.regional.logistic.scheduler.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMailTypeW;
import bbr.b2b.regional.logistic.scheduler.entities.PendingMailType;

public interface IPendingMailTypeServer extends IElementServer<PendingMailType, PendingMailTypeW>  {

	PendingMailTypeW addPendingMailType(PendingMailTypeW pendingmailtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMailTypeW[] getPendingMailTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMailTypeW updatePendingMailType(PendingMailTypeW pendingmailtype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
