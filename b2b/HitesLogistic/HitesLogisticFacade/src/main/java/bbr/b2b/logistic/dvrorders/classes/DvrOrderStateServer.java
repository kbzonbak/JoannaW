package bbr.b2b.logistic.dvrorders.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderState;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderStateType;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateW;

@Stateless(name = "servers/dvrorders/DvrOrderStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrOrderStateServer extends LogisticElementServer<DvrOrderState, DvrOrderStateW> implements DvrOrderStateServerLocal {


	@EJB
	DvrOrderServerLocal dvrorderserver;

	@EJB
	DvrOrderStateTypeServerLocal dvrorderstatetypeserver;

	public DvrOrderStateW addDvrOrderState(DvrOrderStateW dvrorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderStateW) addElement(dvrorderstate);
	}
	public DvrOrderStateW[] getDvrOrderStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderStateW[]) getIdentifiables();
	}
	public DvrOrderStateW updateDvrOrderState(DvrOrderStateW dvrorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderStateW) updateElement(dvrorderstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrOrderState entity, DvrOrderStateW wrapper) {
		wrapper.setDvrorderid(entity.getDvrorder() != null ? new Long(entity.getDvrorder().getId()) : new Long(0));
		wrapper.setDvrorderstatetypeid(entity.getDvrorderstatetype() != null ? new Long(entity.getDvrorderstatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrOrderState entity, DvrOrderStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) { 
			DvrOrder dvrorder = dvrorderserver.getReferenceById(wrapper.getDvrorderid());
			if (dvrorder != null) { 
				entity.setDvrorder(dvrorder);
			}
		}
		if (wrapper.getDvrorderstatetypeid() != null && wrapper.getDvrorderstatetypeid() > 0) { 
			DvrOrderStateType dvrorderstatetype = dvrorderstatetypeserver.getReferenceById(wrapper.getDvrorderstatetypeid());
			if (dvrorderstatetype != null) { 
				entity.setDvrorderstatetype(dvrorderstatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrOrderState";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrOrderState";
	}
}
