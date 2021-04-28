package bbr.b2b.logistic.dvrdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDeliveryState;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateW;

public interface IDvrDeliveryStateServer extends IElementServer<DvrDeliveryState, DvrDeliveryStateW> {

	DvrDeliveryStateW addDvrDeliveryState(DvrDeliveryStateW dvrdeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrDeliveryStateW[] getDvrDeliveryStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrDeliveryStateW updateDvrDeliveryState(DvrDeliveryStateW dvrdeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
