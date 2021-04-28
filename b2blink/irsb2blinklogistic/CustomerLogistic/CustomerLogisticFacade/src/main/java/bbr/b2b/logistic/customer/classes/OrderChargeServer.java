package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.OrderChargeW;
import bbr.b2b.logistic.customer.entities.Order;
import bbr.b2b.logistic.customer.entities.OrderCharge;

@Stateless(name = "servers/customer/OrderChargeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderChargeServer extends LogisticElementServer<OrderCharge, OrderChargeW> implements OrderChargeServerLocal {


	@EJB
	OrderServerLocal orderserver;

	public OrderChargeW addOrderCharge(OrderChargeW ordercharge) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderChargeW) addElement(ordercharge);
	}
	public OrderChargeW[] getOrderCharges() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderChargeW[]) getIdentifiables();
	}
	public OrderChargeW updateOrderCharge(OrderChargeW ordercharge) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderChargeW) updateElement(ordercharge);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderCharge entity, OrderChargeW wrapper) {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderCharge entity, OrderChargeW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) { 
				entity.setOrder(order);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderCharge";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderCharge";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
