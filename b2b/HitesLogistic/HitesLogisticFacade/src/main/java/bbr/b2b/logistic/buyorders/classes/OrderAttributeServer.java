package bbr.b2b.logistic.buyorders.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderAttributeW;
import bbr.b2b.logistic.buyorders.entities.Order;
import bbr.b2b.logistic.buyorders.entities.OrderAttribute;

@Stateless(name = "servers/buyorders/OrderAttributeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderAttributeServer extends LogisticElementServer<OrderAttribute, OrderAttributeW> implements OrderAttributeServerLocal {

	@EJB
	OrderServerLocal orderserver;

	public OrderAttributeW addOrderAttribute(OrderAttributeW orderattribute) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderAttributeW) addElement(orderattribute);
	}
	public OrderAttributeW[] getOrderAttributes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderAttributeW[]) getIdentifiables();
	}
	public OrderAttributeW updateOrderAttribute(OrderAttributeW orderattribute) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderAttributeW) updateElement(orderattribute);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderAttribute entity, OrderAttributeW wrapper) {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(OrderAttribute entity, OrderAttributeW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) { 
				entity.setOrder(order);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderAttribute";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderAttribute";
	}
}
