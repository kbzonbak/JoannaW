package bbr.b2b.logistic.ddcdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateTypeW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryStateType;

public interface IDdcDeliveryStateTypeServer extends IElementServer<DdcDeliveryStateType, DdcDeliveryStateTypeW> {

	DdcDeliveryStateTypeW addDdcDeliveryStateType(DdcDeliveryStateTypeW ddcdeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcDeliveryStateTypeW[] getDdcDeliveryStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcDeliveryStateTypeW updateDdcDeliveryStateType(DdcDeliveryStateTypeW ddcdeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
