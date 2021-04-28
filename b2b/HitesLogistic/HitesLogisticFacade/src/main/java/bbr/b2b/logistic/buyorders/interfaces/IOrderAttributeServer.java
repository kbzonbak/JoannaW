package bbr.b2b.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderAttributeW;
import bbr.b2b.logistic.buyorders.entities.OrderAttribute;

public interface IOrderAttributeServer extends IElementServer<OrderAttribute, OrderAttributeW> {

	OrderAttributeW addOrderAttribute(OrderAttributeW orderattribute) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderAttributeW[] getOrderAttributes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderAttributeW updateOrderAttribute(OrderAttributeW orderattribute) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
