package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryType;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryTypeW;

public interface IDeliveryTypeServer extends IElementServer<DeliveryType, DeliveryTypeW> {

	DeliveryTypeW addDeliveryType(DeliveryTypeW deliverytype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryTypeW[] getDeliveryTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryTypeW updateDeliveryType(DeliveryTypeW deliverytype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
