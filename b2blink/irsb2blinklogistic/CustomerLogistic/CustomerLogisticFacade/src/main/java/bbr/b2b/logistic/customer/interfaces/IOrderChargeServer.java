package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.OrderCharge;
import bbr.b2b.logistic.customer.data.wrappers.OrderChargeW;

public interface IOrderChargeServer extends IElementServer<OrderCharge, OrderChargeW> {

	OrderChargeW addOrderCharge(OrderChargeW ordercharge) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderChargeW[] getOrderCharges() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderChargeW updateOrderCharge(OrderChargeW ordercharge) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
