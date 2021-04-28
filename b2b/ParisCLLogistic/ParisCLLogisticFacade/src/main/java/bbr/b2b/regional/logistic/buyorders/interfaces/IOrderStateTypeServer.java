package bbr.b2b.regional.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.entities.OrderStateType;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;

public interface IOrderStateTypeServer extends IElementServer<OrderStateType, OrderStateTypeW> {

	OrderStateTypeW addOrderStateType(OrderStateTypeW orderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderStateTypeW[] getOrderStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderStateTypeW updateOrderStateType(OrderStateTypeW orderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
