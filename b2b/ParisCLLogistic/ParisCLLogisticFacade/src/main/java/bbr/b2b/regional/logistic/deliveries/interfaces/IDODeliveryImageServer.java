package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryImageW;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryImage;

public interface IDODeliveryImageServer extends IElementServer<DODeliveryImage, DODeliveryImageW> {

	DODeliveryImageW addDODeliveryImage(DODeliveryImageW dodeliveryimage) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryImageW[] getDODeliverysImage() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryImageW updateDODeliveryImage(DODeliveryImageW dodeliveryimage) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
