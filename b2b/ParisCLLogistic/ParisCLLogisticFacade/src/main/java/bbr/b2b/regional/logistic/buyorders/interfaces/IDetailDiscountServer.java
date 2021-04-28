package bbr.b2b.regional.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.entities.DetailDiscount;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.DetailDiscountW;

public interface IDetailDiscountServer extends IElementServer<DetailDiscount, DetailDiscountW> {

	DetailDiscountW addDetailDiscount(DetailDiscountW detaildiscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailDiscountW[] getDetailDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailDiscountW updateDetailDiscount(DetailDiscountW detaildiscount) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailDiscountDTO[] getDetailDiscountByOrders(Long[] orderids) throws OperationFailedException, NotFoundException;
	DetailDiscountReportDataDTO[] getDetailDiscountsOfOrderDetail(Long orderid, Long itemid, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
