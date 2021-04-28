package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.Box;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BoxW;

public interface IBoxServer extends IElementServer<Box, BoxW> {

	BoxW addBox(BoxW box) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BoxW[] getBoxs() throws AccessDeniedException, OperationFailedException, NotFoundException;
	BoxW updateBox(BoxW box) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int deleteByDeliveryId(Long deliveryid);
}
