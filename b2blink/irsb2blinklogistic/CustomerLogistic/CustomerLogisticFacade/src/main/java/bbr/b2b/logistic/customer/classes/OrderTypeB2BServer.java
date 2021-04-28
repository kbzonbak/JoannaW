package bbr.b2b.logistic.customer.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.OrderTypeB2BW;
import bbr.b2b.logistic.customer.entities.OrderTypeB2B;

@Stateless(name = "servers/customer/OrderTypeB2BServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderTypeB2BServer extends LogisticElementServer<OrderTypeB2B, OrderTypeB2BW> implements OrderTypeB2BServerLocal {


	public OrderTypeB2BW addOrderTypeB2B(OrderTypeB2BW ordertypeb2b) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeB2BW) addElement(ordertypeb2b);
	}
	public OrderTypeB2BW[] getOrderTypeB2Bs() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeB2BW[]) getIdentifiables();
	}
	public OrderTypeB2BW updateOrderTypeB2B(OrderTypeB2BW ordertypeb2b) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeB2BW) updateElement(ordertypeb2b);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderTypeB2B entity, OrderTypeB2BW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderTypeB2B entity, OrderTypeB2BW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderTypeB2B";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderTypeB2B";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
