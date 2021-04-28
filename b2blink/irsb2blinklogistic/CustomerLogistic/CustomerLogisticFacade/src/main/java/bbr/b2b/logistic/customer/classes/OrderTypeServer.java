package bbr.b2b.logistic.customer.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.entities.OrderType;
import bbr.b2b.logistic.customer.entities.OrderTypeB2B;

@Stateless(name = "servers/customer/OrderTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderTypeServer extends LogisticElementServer<OrderType, OrderTypeW> implements OrderTypeServerLocal {

	@EJB
	BuyerServerLocal buyerserver;
	
	@EJB
	OrderTypeB2BServerLocal ordertypeb2bserver;
	
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
		wrapper.setBuyerid(entity.getBuyer() != null ? new Long(entity.getBuyer().getId()) : new Long(0));
		wrapper.setOrdertypeb2bid(entity.getOrdertypeb2b() != null ? new Long(entity.getOrdertypeb2b().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderType entity, OrderTypeW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getBuyerid() != null && wrapper.getBuyerid() > 0) { 
			Buyer buyer = buyerserver.getReferenceById(wrapper.getBuyerid());
			if (buyer != null) { 
				entity.setBuyer(buyer);
			}
		}
		if (wrapper.getOrdertypeb2bid() != null && wrapper.getOrdertypeb2bid() > 0) { 
			OrderTypeB2B ordertypeb2b = ordertypeb2bserver.getReferenceById(wrapper.getOrdertypeb2bid());
			if (ordertypeb2b != null) { 
				entity.setOrdertypeb2b(ordertypeb2b);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderType";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderType";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
