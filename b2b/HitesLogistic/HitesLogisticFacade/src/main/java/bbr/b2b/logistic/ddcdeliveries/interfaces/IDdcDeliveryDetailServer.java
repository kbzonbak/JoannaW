package bbr.b2b.logistic.ddcdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryDetailW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryDetail;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryDetailId;
public interface IDdcDeliveryDetailServer extends IGenericServer<DdcDeliveryDetail, DdcDeliveryDetailId, DdcDeliveryDetailW> {

	DdcDeliveryDetailW addDdcDeliveryDetail(DdcDeliveryDetailW ddcdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcDeliveryDetailW[] getDdcDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcDeliveryDetailW updateDdcDeliveryDetail(DdcDeliveryDetailW ddcdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
