package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateW;
import bbr.b2b.logistic.customer.entities.Order;
import bbr.b2b.logistic.customer.entities.SoaState;
import bbr.b2b.logistic.customer.entities.SoaStateType;

@Stateless(name = "servers/customer/SoaStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SoaStateServer extends LogisticElementServer<SoaState, SoaStateW> implements SoaStateServerLocal {


	@EJB
	SoaStateTypeServerLocal soastatetypeserver;

	@EJB
	OrderServerLocal orderserver;

	public SoaStateW addSoaState(SoaStateW soastate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaStateW) addElement(soastate);
	}
	public SoaStateW[] getSoaStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaStateW[]) getIdentifiables();
	}
	public SoaStateW updateSoaState(SoaStateW soastate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaStateW) updateElement(soastate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(SoaState entity, SoaStateW wrapper) {
		wrapper.setSoastatetypeid(entity.getSoastatetype() != null ? new Long(entity.getSoastatetype().getId()) : new Long(0));
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(SoaState entity, SoaStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getSoastatetypeid() != null && wrapper.getSoastatetypeid() > 0) { 
			SoaStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getSoastatetypeid());
			if (soastatetype != null) { 
				entity.setSoastatetype(soastatetype);
			}
		}
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) { 
				entity.setOrder(order);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "SoaState";
	}
	@Override
	protected void setEntityname() {
		entityname = "SoaState";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
