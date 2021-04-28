package bbr.b2b.logistic.dvrorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscountDvrOrderDetail;
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountDvrOrderDetailW;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscountDvrOrderDetailId;
import bbr.b2b.logistic.dvrorders.report.classes.ChargeDiscountDataByDvrOrderDetailDTO;
public interface IChargeDiscountDvrOrderDetailServer extends IGenericServer<ChargeDiscountDvrOrderDetail, ChargeDiscountDvrOrderDetailId, ChargeDiscountDvrOrderDetailW> {

	ChargeDiscountDvrOrderDetailW addChargeDiscountDvrOrderDetail(ChargeDiscountDvrOrderDetailW chargediscountdvrorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChargeDiscountDvrOrderDetailW[] getChargeDiscountDvrOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChargeDiscountDvrOrderDetailW updateChargeDiscountDvrOrderDetail(ChargeDiscountDvrOrderDetailW chargediscountdvrorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChargeDiscountDataByDvrOrderDetailDTO[] getChargeDiscountDataFromOrderDetail(Long dvrorderid, Long itemid, Integer position);
	
}
