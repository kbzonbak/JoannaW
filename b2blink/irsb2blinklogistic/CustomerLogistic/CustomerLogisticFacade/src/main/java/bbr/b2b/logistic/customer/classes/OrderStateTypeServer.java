package bbr.b2b.logistic.customer.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.OrderStateTypeW;
import bbr.b2b.logistic.customer.entities.OrderStateType;

@Stateless(name = "servers/customer/OrderStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderStateTypeServer extends LogisticElementServer<OrderStateType, OrderStateTypeW> implements OrderStateTypeServerLocal {


	public OrderStateTypeW addOrderStateType(OrderStateTypeW orderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderStateTypeW) addElement(orderstatetype);
	}
	public OrderStateTypeW[] getOrderStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderStateTypeW[]) getIdentifiables();
	}
	public OrderStateTypeW updateOrderStateType(OrderStateTypeW orderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderStateTypeW) updateElement(orderstatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderStateType entity, OrderStateTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderStateType entity, OrderStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderStateType";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
