package bbr.b2b.regional.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.entities.OrderType;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderTypeW;

public interface IOrderTypeServer extends IElementServer<OrderType, OrderTypeW> {

	OrderTypeW addOrderType(OrderTypeW ordertype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderTypeW[] getOrderTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderTypeW updateOrderType(OrderTypeW ordertype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
