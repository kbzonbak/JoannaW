package bbr.b2b.logistic.customer.classes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.PendingMessageW;
import bbr.b2b.logistic.customer.entities.PendingMessage;
import bbr.b2b.logistic.customer.entities.PendingMessageType;

@Stateless(name = "servers/customer/PendingMessageServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMessageServer extends LogisticElementServer<PendingMessage, PendingMessageW> implements PendingMessageServerLocal {


	@EJB
	PendingMessageTypeServerLocal pendingmessagetypeserver;

	public PendingMessageW addPendingMessage(PendingMessageW pendingmessage) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageW) addElement(pendingmessage);
	}
	public PendingMessageW[] getPendingMessages() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageW[]) getIdentifiables();
	}
	public PendingMessageW updatePendingMessage(PendingMessageW pendingmessage) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMessageW) updateElement(pendingmessage);
	}

	@Override
	protected void copyRelationsEntityToWrapper(PendingMessage entity, PendingMessageW wrapper) {
		wrapper.setPendingmessagetypeid(entity.getPendingmessagetype() != null ? new Long(entity.getPendingmessagetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(PendingMessage entity, PendingMessageW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getPendingmessagetypeid() != null && wrapper.getPendingmessagetypeid() > 0) { 
			PendingMessageType pendingmessagetype = pendingmessagetypeserver.getReferenceById(wrapper.getPendingmessagetypeid());
			if (pendingmessagetype != null) { 
				entity.setPendingmessagetype(pendingmessagetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "PendingMessage";
	}
	@Override
	protected void setEntityname() {
		entityname = "PendingMessage";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public PendingMessageW[] getMessageToSend(int size) throws OperationFailedException, NotFoundException {
		StringBuilder sb = new StringBuilder("select pm From PendingMessage as pm " +//
				" where pm.status=0 " +//
				" order by pm.pendingmessagetype.priority asc");
		
		List<PendingMessageW> pmList= getByQuery(1, size, sb.toString());
		
		return (PendingMessageW[]) pmList.toArray(new PendingMessageW[pmList.size()]);
	}
	@Override
	public int doDeletePendingMessage() {
		// TODO Auto-generated method stub
		return 0;
	}
}
