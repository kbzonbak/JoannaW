package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.OrderTypeB2B;
import bbr.b2b.logistic.customer.data.wrappers.OrderTypeB2BW;

public interface IOrderTypeB2BServer extends IElementServer<OrderTypeB2B, OrderTypeB2BW> {

	OrderTypeB2BW addOrderTypeB2B(OrderTypeB2BW ordertypeb2b) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderTypeB2BW[] getOrderTypeB2Bs() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderTypeB2BW updateOrderTypeB2B(OrderTypeB2BW ordertypeb2b) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
