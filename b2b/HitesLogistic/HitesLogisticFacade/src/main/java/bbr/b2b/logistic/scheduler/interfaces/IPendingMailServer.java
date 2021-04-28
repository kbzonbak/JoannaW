package bbr.b2b.logistic.scheduler.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.scheduler.entities.PendingMail;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMailW;

public interface IPendingMailServer extends IElementServer<PendingMail, PendingMailW> {

	PendingMailW addPendingMail(PendingMailW pendingmail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMailW[] getPendingMails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PendingMailW updatePendingMail(PendingMailW pendingmail) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
