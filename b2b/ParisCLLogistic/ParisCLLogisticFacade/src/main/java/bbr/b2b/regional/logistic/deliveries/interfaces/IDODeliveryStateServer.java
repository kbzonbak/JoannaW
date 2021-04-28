package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryState;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateW;

public interface IDODeliveryStateServer extends IElementServer<DODeliveryState, DODeliveryStateW> {

	DODeliveryStateW addDODeliveryState(DODeliveryStateW dodeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryStateW[] getDODeliveryStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DODeliveryStateW updateDODeliveryState(DODeliveryStateW dodeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
