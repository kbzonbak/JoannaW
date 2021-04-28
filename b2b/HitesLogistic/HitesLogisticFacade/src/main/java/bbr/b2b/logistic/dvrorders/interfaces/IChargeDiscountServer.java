package bbr.b2b.logistic.dvrorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscount;
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountW;

public interface IChargeDiscountServer extends IElementServer<ChargeDiscount, ChargeDiscountW> {

	ChargeDiscountW addChargeDiscount(ChargeDiscountW chargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChargeDiscountW[] getChargeDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChargeDiscountW updateChargeDiscount(ChargeDiscountW chargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
