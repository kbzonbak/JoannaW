package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.OrderDiscountW;
import bbr.b2b.logistic.customer.entities.Order;
import bbr.b2b.logistic.customer.entities.OrderDiscount;

@Stateless(name = "servers/customer/OrderDiscountServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderDiscountServer extends LogisticElementServer<OrderDiscount, OrderDiscountW> implements OrderDiscountServerLocal {


	@EJB
	OrderServerLocal orderserver;

	public OrderDiscountW addOrderDiscount(OrderDiscountW orderdiscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDiscountW) addElement(orderdiscount);
	}
	public OrderDiscountW[] getOrderDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDiscountW[]) getIdentifiables();
	}
	public OrderDiscountW updateOrderDiscount(OrderDiscountW orderdiscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDiscountW) updateElement(orderdiscount);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderDiscount entity, OrderDiscountW wrapper) {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderDiscount entity, OrderDiscountW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) { 
				entity.setOrder(order);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderDiscount";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderDiscount";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
