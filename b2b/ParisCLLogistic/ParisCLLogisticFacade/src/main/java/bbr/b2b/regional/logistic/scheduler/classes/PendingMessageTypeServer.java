package bbr.b2b.regional.logistic.scheduler.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.scheduler.data.wrappers.PendingMessageTypeW;
import bbr.b2b.regional.logistic.scheduler.entities.PendingMessageType;

@Stateless(name = "servers/scheduler/PendingMessageTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMessageTypeServer extends LogisticElementServer<PendingMessageType, PendingMessageTypeW> implements PendingMessageTypeServerLocal {

	public PendingMessageTypeW addPendingMessageType(PendingMessageTypeW pendingstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageTypeW) addElement(pendingstatetype);
	}

	public PendingMessageTypeW[] getPendingMessageTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageTypeW[]) getIdentifiables();
	}

	public PendingMessageTypeW updatePendingMessageType(PendingMessageTypeW pendingstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageTypeW) updateElement(pendingstatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(PendingMessageType entity, PendingMessageTypeW wrapper) {
	}

	@Override
	protected void copyRelationsWrapperToEntity(PendingMessageType entity, PendingMessageTypeW wrapper) throws OperationFailedException, NotFoundException {

	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "PendingMessageType";
	}

	@Override
	protected void setEntityname() {
		entityname = "PendingMessageType";
	}

}
