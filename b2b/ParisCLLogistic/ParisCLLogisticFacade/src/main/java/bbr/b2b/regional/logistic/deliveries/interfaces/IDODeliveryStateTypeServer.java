package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryStateType;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;

public interface IDODeliveryStateTypeServer extends IElementServer<DODeliveryStateType, DODeliveryStateTypeW> {

	DODeliveryStateTypeW addDODeliveryStateType(DODeliveryStateTypeW dodeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryStateTypeW[] getDODeliveryStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryStateTypeW updateDODeliveryStateType(DODeliveryStateTypeW dodeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
