package bbr.b2b.logistic.scheduler.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.scheduler.data.wrappers.PendingMailW;
import bbr.b2b.logistic.scheduler.entities.PendingMail;
import bbr.b2b.logistic.scheduler.entities.PendingMailType;

@Stateless(name = "servers/scheduler/PendingMailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendingMailServer extends LogisticElementServer<PendingMail, PendingMailW> implements PendingMailServerLocal {


	@EJB
	PendingMailTypeServerLocal pendingmailtypeserver;

	public PendingMailW addPendingMail(PendingMailW pendingmail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailW) addElement(pendingmail);
	}
	public PendingMailW[] getPendingMails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailW[]) getIdentifiables();
	}
	public PendingMailW updatePendingMail(PendingMailW pendingmail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PendingMailW) updateElement(pendingmail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(PendingMail entity, PendingMailW wrapper) {
		wrapper.setPendingmailtypeid(entity.getPendingmailtype() != null ? new Long(entity.getPendingmailtype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(PendingMail entity, PendingMailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getPendingmailtypeid() != null && wrapper.getPendingmailtypeid() > 0) { 
			PendingMailType pendingmailtype = pendingmailtypeserver.getReferenceById(wrapper.getPendingmailtypeid());
			if (pendingmailtype != null) { 
				entity.setPendingmailtype(pendingmailtype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "PendingMail";
	}
	@Override
	protected void setEntityname() {
		entityname = "PendingMail";
	}
}
