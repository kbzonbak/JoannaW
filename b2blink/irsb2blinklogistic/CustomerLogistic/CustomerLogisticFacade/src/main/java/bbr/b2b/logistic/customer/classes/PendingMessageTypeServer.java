package bbr.b2b.logistic.customer.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.PendingMessageTypeW;
import bbr.b2b.logistic.customer.entities.PendingMessageType;

@Stateless(name = "servers/customer/PendingMessageTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMessageTypeServer extends LogisticElementServer<PendingMessageType, PendingMessageTypeW> implements PendingMessageTypeServerLocal {


	public PendingMessageTypeW addPendingMessageType(PendingMessageTypeW pendingmessagetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageTypeW) addElement(pendingmessagetype);
	}
	public PendingMessageTypeW[] getPendingMessageTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageTypeW[]) getIdentifiables();
	}
	public PendingMessageTypeW updatePendingMessageType(PendingMessageTypeW pendingmessagetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageTypeW) updateElement(pendingmessagetype);
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
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
