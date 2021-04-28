package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.OrderDiscount;
import bbr.b2b.logistic.customer.data.wrappers.OrderDiscountW;

public interface IOrderDiscountServer extends IElementServer<OrderDiscount, OrderDiscountW> {

	OrderDiscountW addOrderDiscount(OrderDiscountW orderdiscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDiscountW[] getOrderDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDiscountW updateOrderDiscount(OrderDiscountW orderdiscount) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
