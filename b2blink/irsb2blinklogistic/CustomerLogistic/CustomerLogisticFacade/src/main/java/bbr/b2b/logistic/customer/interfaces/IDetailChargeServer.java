package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.customer.entities.DetailCharge;
import bbr.b2b.logistic.customer.data.wrappers.DetailChargeW;
import bbr.b2b.logistic.customer.entities.DetailchargeId;

public interface IDetailChargeServer extends IGenericServer<DetailCharge, DetailchargeId, DetailChargeW> {

	DetailChargeW addDetailCharge(DetailChargeW detailcharge) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailChargeW[] getDetailCharges() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DetailChargeW updateDetailCharge(DetailChargeW detailcharge) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
