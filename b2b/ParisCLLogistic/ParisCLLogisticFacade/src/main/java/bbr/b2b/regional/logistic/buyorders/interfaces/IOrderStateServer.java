package bbr.b2b.regional.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.entities.OrderState;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateW;

public interface IOrderStateServer extends IElementServer<OrderState, OrderStateW> {

	OrderStateW addOrderState(OrderStateW orderstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderStateW[] getOrderStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderStateW updateOrderState(OrderStateW orderstate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
