package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.customer.entities.DetailDiscount;
import bbr.b2b.logistic.customer.data.wrappers.DetailDiscountW;
import bbr.b2b.logistic.customer.entities.DetaildiscountId;

public interface IDetailDiscountServer extends IGenericServer<DetailDiscount, DetaildiscountId, DetailDiscountW> {

	DetailDiscountW addDetailDiscount(DetailDiscountW detaildiscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailDiscountW[] getDetailDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailDiscountW updateDetailDiscount(DetailDiscountW detaildiscount) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
