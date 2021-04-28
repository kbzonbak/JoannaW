package bbr.b2b.logistic.dvrdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDeliveryStateType;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateTypeW;

public interface IDvrDeliveryStateTypeServer extends IElementServer<DvrDeliveryStateType, DvrDeliveryStateTypeW> {

	DvrDeliveryStateTypeW addDvrDeliveryStateType(DvrDeliveryStateTypeW dvrdeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrDeliveryStateTypeW[] getDvrDeliveryStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrDeliveryStateTypeW updateDvrDeliveryStateType(DvrDeliveryStateTypeW dvrdeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
