package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;

public interface IBuyerServer extends IElementServer<Buyer, BuyerW> {

	BuyerW addBuyer(BuyerW buyer) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BuyerW[] getBuyers() throws AccessDeniedException, OperationFailedException, NotFoundException;
	BuyerW updateBuyer(BuyerW buyer) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
