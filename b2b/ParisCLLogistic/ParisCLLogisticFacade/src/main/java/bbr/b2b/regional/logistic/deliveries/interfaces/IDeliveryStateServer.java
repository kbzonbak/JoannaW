package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryState;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateW;

public interface IDeliveryStateServer extends IElementServer<DeliveryState, DeliveryStateW> {

	DeliveryStateW addDeliveryState(DeliveryStateW deliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryStateW[] getDeliveryStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryStateW updateDeliveryState(DeliveryStateW deliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
