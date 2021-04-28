package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.Bulk;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulksCountAndAvailableUnitsDataDTO;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkW;

public interface IBulkServer extends IElementServer<Bulk, BulkW> {

	BulkW addBulk(BulkW bulk) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkW[] getBulks() throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkW updateBulk(BulkW bulk) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulksCountAndAvailableUnitsDataDTO[] getBulksCountAndAvailableUnitsByDelivery(Long[] deliveryids) throws ServiceException;
	BulkDTO[] getBulksByOrderDeliveries(Long deliveryid, Long[] orderids);
}
