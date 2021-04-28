package bbr.b2b.logistic.scheduler.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.scheduler.entities.PendingMailType;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMailTypeW;

@Stateless(name = "servers/scheduler/PendingMailTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMailTypeServer extends LogisticElementServer<PendingMailType, PendingMailTypeW> implements PendingMailTypeServerLocal {


	public PendingMailTypeW addPendingMailType(PendingMailTypeW pendingmailtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailTypeW) addElement(pendingmailtype);
	}
	public PendingMailTypeW[] getPendingMailTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailTypeW[]) getIdentifiables();
	}
	public PendingMailTypeW updatePendingMailType(PendingMailTypeW pendingmailtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailTypeW) updateElement(pendingmailtype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(PendingMailType entity, PendingMailTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(PendingMailType entity, PendingMailTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "PendingMailType";
	}
	@Override
	protected void setEntityname() {
		entityname = "PendingMailType";
	}
}
