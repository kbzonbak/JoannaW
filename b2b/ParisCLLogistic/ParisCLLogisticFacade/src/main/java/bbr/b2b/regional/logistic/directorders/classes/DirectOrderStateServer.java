package bbr.b2b.regional.logistic.directorders.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateW;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderState;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderStateType;

@Stateless(name = "servers/directorders/DirectOrderStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DirectOrderStateServer extends LogisticElementServer<DirectOrderState, DirectOrderStateW> implements DirectOrderStateServerLocal {


	@EJB
	DirectOrderServerLocal directorderserver;

	@EJB
	DirectOrderStateTypeServerLocal directorderstatetypeserver;

	public DirectOrderStateW addDirectOrderState(DirectOrderStateW directorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderStateW) addElement(directorderstate);
	}
	public DirectOrderStateW[] getDirectOrderStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderStateW[]) getIdentifiables();
	}
	public DirectOrderStateW updateDirectOrderState(DirectOrderStateW directorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderStateW) updateElement(directorderstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DirectOrderState entity, DirectOrderStateW wrapper) {
		wrapper.setOrderid(entity.getDirectorder() != null ? new Long(entity.getDirectorder().getId()) : new Long(0));
		wrapper.setOrderstatetypeid(entity.getDirectorderstatetype() != null ? new Long(entity.getDirectorderstatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DirectOrderState entity, DirectOrderStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			DirectOrder directorder = directorderserver.getReferenceById(wrapper.getOrderid());
			if (directorder != null) { 
				entity.setDirectorder(directorder);
			}
		}
		if (wrapper.getOrderstatetypeid() != null && wrapper.getOrderstatetypeid() > 0) { 
			DirectOrderStateType directorderstatetype = directorderstatetypeserver.getReferenceById(wrapper.getOrderstatetypeid());
			if (directorderstatetype != null) { 
				entity.setDirectorderstatetype(directorderstatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DirectOrderState";
	}
	@Override
	protected void setEntityname() {
		entityname = "DirectOrderState";
	}
}
