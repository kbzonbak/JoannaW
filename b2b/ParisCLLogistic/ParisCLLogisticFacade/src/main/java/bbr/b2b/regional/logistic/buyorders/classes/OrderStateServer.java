package bbr.b2b.regional.logistic.buyorders.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateW;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.buyorders.entities.OrderState;
import bbr.b2b.regional.logistic.buyorders.entities.OrderStateType;

@Stateless(name = "servers/buyorders/OrderStateServer")
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
}
