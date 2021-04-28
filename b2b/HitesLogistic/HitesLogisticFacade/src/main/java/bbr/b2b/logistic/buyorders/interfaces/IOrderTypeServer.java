package bbr.b2b.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.buyorders.entities.OrderType;

public interface IOrderTypeServer extends IElementServer<OrderType, OrderTypeW> {

	OrderTypeW addDvrOrderType(OrderTypeW dvrordertype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderTypeW[] getDvrOrderTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderTypeW updateDvrOrderType(OrderTypeW dvrordertype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderTypeW[] getOrderTypeByDvrOrders(Long[] dvrorderids) throws NotFoundException, OperationFailedException; 
}
