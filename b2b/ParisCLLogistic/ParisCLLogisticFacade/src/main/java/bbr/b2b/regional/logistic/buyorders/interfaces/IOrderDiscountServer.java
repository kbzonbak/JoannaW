package bbr.b2b.regional.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDiscountW;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDiscount;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDiscountDTO;

public interface IOrderDiscountServer extends IElementServer<OrderDiscount, OrderDiscountW> {

	OrderDiscountW addOrderDiscount(OrderDiscountW orderdiscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDiscountW[] getOrderDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDiscountW updateOrderDiscount(OrderDiscountW orderdiscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDiscountDTO[] getOrderDiscountByOrders(Long[] orderids) throws OperationFailedException, NotFoundException ;

}
