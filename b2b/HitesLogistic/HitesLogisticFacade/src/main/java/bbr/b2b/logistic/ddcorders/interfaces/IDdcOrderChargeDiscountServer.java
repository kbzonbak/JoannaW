package bbr.b2b.logistic.ddcorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderChargeDiscountW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderChargeDiscount;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderChargeDiscountId;

public interface IDdcOrderChargeDiscountServer extends IGenericServer<DdcOrderChargeDiscount, DdcOrderChargeDiscountId, DdcOrderChargeDiscountW> {

	DdcOrderChargeDiscountW addDdcOrderChargeDiscount(DdcOrderChargeDiscountW ddcorderchargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderChargeDiscountW[] getDdcOrderChargeDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderChargeDiscountW updateDdcOrderChargeDiscount(DdcOrderChargeDiscountW ddcorderchargediscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
