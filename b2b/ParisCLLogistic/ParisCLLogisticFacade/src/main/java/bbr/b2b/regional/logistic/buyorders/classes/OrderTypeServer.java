package bbr.b2b.regional.logistic.buyorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.regional.logistic.buyorders.entities.OrderType;

@Stateless(name = "servers/buyorders/OrderTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderTypeServer extends LogisticElementServer<OrderType, OrderTypeW> implements OrderTypeServerLocal {


	public OrderTypeW addOrderType(OrderTypeW ordertype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeW) addElement(ordertype);
	}
	public OrderTypeW[] getOrderTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeW[]) getIdentifiables();
	}
	public OrderTypeW updateOrderType(OrderTypeW ordertype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeW) updateElement(ordertype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderType entity, OrderTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderType entity, OrderTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderType";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderType";
	}
}
