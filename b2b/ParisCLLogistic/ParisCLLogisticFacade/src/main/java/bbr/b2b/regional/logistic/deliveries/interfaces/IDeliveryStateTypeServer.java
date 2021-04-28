package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryStateType;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;

public interface IDeliveryStateTypeServer extends IElementServer<DeliveryStateType, DeliveryStateTypeW> {

	DeliveryStateTypeW addDeliveryStateType(DeliveryStateTypeW deliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryStateTypeW[] getDeliveryStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryStateTypeW updateDeliveryStateType(DeliveryStateTypeW deliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
