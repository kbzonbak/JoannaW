package bbr.b2b.regional.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.entities.Discount;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.DiscountW;

public interface IDiscountServer extends IElementServer<Discount, DiscountW> {

	DiscountW addDiscount(DiscountW discount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DiscountW[] getDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DiscountW updateDiscount(DiscountW discount) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
