package bbr.b2b.logistic.soa.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.logistic.buyorders.entities.Order;
import bbr.b2b.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.logistic.soa.entities.SOAState;
import bbr.b2b.logistic.soa.entities.SOAStateType;

@Stateless(name = "servers/SOAStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SOAStateServer extends LogisticElementServer<SOAState, SOAStateW> implements SOAStateServerLocal {

	@EJB
	OrderServerLocal orderserver;

	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	public SOAStateW addSOAState(SOAStateW soastate) throws OperationFailedException, NotFoundException {
		return addElement(soastate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(SOAState entity, SOAStateW wrapper) throws OperationFailedException, NotFoundException {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
		wrapper.setSoastatetypeid(entity.getSoastatetype() != null ? new Long(entity.getSoastatetype().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(SOAState entity, SOAStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() > 0) {
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) {
				entity.setOrder(order);
			}
		}
		if (wrapper.getSoastatetypeid() > 0) {
			SOAStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getSoastatetypeid());
			if (soastatetype != null) {
				entity.setSoastatetype(soastatetype);
			}
		}
	}

	public SOAStateW[] getSOAStates() throws OperationFailedException, NotFoundException {
		return getIdentifiables();
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Estado de SOA";
	}

	@Override
	protected void setEntityname() {
		entityname = "SOAState";
	}

	public SOAStateW updateSOAState(SOAStateW soastate) throws OperationFailedException, NotFoundException {
		return (SOAStateW) updateElement(soastate);
	}
}
