package bbr.b2b.logistic.ddcdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDelivery;

public interface IDdcDeliveryServer extends IElementServer<DdcDelivery, DdcDeliveryW> {

	DdcDeliveryW addDdcDelivery(DdcDeliveryW ddcdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcDeliveryW[] getDdcDeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcDeliveryW updateDdcDelivery(DdcDeliveryW ddcdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	Long getNextSequenceDeliveryNumber() throws OperationFailedException, NotFoundException;
	DdcDeliveryW[] getDdcDeliveryByOrderids(Long[] ddcorderids) throws NotFoundException, OperationFailedException;
}
