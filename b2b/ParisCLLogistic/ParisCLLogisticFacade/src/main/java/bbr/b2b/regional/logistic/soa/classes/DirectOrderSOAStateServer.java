package bbr.b2b.regional.logistic.soa.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.soa.data.classes.DirectOrderSOAStateW;
import bbr.b2b.regional.logistic.soa.entities.DirectOrderSOAState;
import bbr.b2b.regional.logistic.soa.entities.SOAStateType;

@Stateless(name = "servers/DirectOrderSOAStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DirectOrderSOAStateServer extends LogisticElementServer<DirectOrderSOAState, DirectOrderSOAStateW> implements DirectOrderSOAStateServerLocal {

	@EJB
	DirectOrderServerLocal directorderserver;

	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	public DirectOrderSOAStateW addSOAState(DirectOrderSOAStateW soastate) throws OperationFailedException, NotFoundException {
		return (DirectOrderSOAStateW) addElement(soastate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DirectOrderSOAState entity, DirectOrderSOAStateW wrapper) throws OperationFailedException, NotFoundException {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
		wrapper.setSoastatetypeid(entity.getSoastatetype() != null ? new Long(entity.getSoastatetype().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(DirectOrderSOAState entity, DirectOrderSOAStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() > 0) {
			DirectOrder order = directorderserver.getReferenceById(wrapper.getOrderid());
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

	public DirectOrderSOAStateW[] getSOAStates() throws OperationFailedException, NotFoundException {
		return (DirectOrderSOAStateW[]) getIdentifiables();
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Estado de SOA";
	}

	@Override
	protected void setEntityname() {
		entityname = "SOAState";
	}

	public DirectOrderSOAStateW updateSOAState(DirectOrderSOAStateW soastate) throws OperationFailedException, NotFoundException {
		return (DirectOrderSOAStateW) updateElement(soastate);
	}
}
