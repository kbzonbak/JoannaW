package bbr.b2b.logistic.dvrdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDelivery;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryId;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderToDeliveryUnitsDTO;
public interface IDvrOrderDeliveryServer extends IGenericServer<DvrOrderDelivery, DvrOrderDeliveryId, DvrOrderDeliveryW> {

	DvrOrderDeliveryW addDvrOrderDelivery(DvrOrderDeliveryW dvrorderdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderDeliveryW[] getDvrOrderDeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderDeliveryW updateDvrOrderDelivery(DvrOrderDeliveryW dvrorderdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderToDeliveryUnitsDTO[] getDvrOrderDeliveryUnits(Long dvrdeliveryid);
	void doUpdateEstimatedUnits(Long dvrdeliveryid);
}
