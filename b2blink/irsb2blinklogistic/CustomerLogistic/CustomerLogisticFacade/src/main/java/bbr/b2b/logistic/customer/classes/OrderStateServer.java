package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.OrderStateW;
import bbr.b2b.logistic.customer.entities.Order;
import bbr.b2b.logistic.customer.entities.OrderState;
import bbr.b2b.logistic.customer.entities.OrderStateType;

@Stateless(name = "servers/customer/OrderStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderStateServer extends LogisticElementServer<OrderState, OrderStateW> implements OrderStateServerLocal {


	@EJB
	OrderServerLocal orderserver;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	public OrderStateW addOrderState(OrderStateW orderstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderStateW) addElement(orderstate);
	}
	public OrderStateW[] getOrderStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderStateW[]) getIdentifiables();
	}
	public OrderStateW updateOrderState(OrderStateW orderstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderStateW) updateElement(orderstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderState entity, OrderStateW wrapper) {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
		wrapper.setOrderstatetypeid(entity.getOrderstatetype() != null ? new Long(entity.getOrderstatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderState entity, OrderStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) { 
				entity.setOrder(order);
			}
		}
		if (wrapper.getOrderstatetypeid() != null && wrapper.getOrderstatetypeid() > 0) { 
			OrderStateType orderstatetype = orderstatetypeserver.getReferenceById(wrapper.getOrderstatetypeid());
			if (orderstatetype != null) { 
				entity.setOrderstatetype(orderstatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderState";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderState";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
