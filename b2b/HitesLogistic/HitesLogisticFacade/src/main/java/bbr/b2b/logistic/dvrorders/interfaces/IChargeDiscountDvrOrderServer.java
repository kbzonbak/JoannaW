package bbr.b2b.logistic.dvrorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscountDvrOrder;
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountDvrOrderW;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscountDvrOrderId;
import bbr.b2b.logistic.dvrorders.report.classes.DiscountByOrderDataDTO;


public interface IChargeDiscountDvrOrderServer extends IGenericServer<ChargeDiscountDvrOrder, ChargeDiscountDvrOrderId, ChargeDiscountDvrOrderW> {

	ChargeDiscountDvrOrderW addChargeDiscountDvrOrder(ChargeDiscountDvrOrderW chargediscountdvrorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChargeDiscountDvrOrderW[] getChargeDiscountDvrOrders() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChargeDiscountDvrOrderW updateChargeDiscountDvrOrder(ChargeDiscountDvrOrderW chargediscountdvrorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DiscountByOrderDataDTO[] getDiscountDataFromOrder(Long dvrorderid, Long vendorid);
}
