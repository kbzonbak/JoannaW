package bbr.b2b.logistic.ddcorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcChargeDiscountW;
import bbr.b2b.logistic.ddcorders.entities.DdcChargeDiscount;

public interface IDdcChargeDiscountServer extends IElementServer<DdcChargeDiscount, DdcChargeDiscountW> {

	DdcChargeDiscountW addDdcChargeDiscount(DdcChargeDiscountW ddcchargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcChargeDiscountW[] getDdcChargeDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcChargeDiscountW updateDdcChargeDiscount(DdcChargeDiscountW ddcchargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
