package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedNonDeliveredOrderDTO;
import bbr.b2b.regional.logistic.datings.report.classes.OrderUnitsDTO;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDelivery;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryId;
public interface IOrderDeliveryServer extends IGenericServer<OrderDelivery, OrderDeliveryId, OrderDeliveryW> {

	OrderDeliveryW addOrderDelivery(OrderDeliveryW orderdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDeliveryW[] getOrderDeliveries() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDeliveryW[] getOrderDeliveriesByOrderRefDocumentAndContainer(Long orderid, Long guidenumber, String containernumber) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDeliveryW[] getOrderDeliveriesOfOrder(Long orderid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDeliveryW[] getOrderDeliveriesOfOrder(Long[] orderid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDeliveryW updateOrderDelivery(OrderDeliveryW orderdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDeliveryW[] getOrderDeliveriesByOrderAndDelivery(Long orderid, Long deliveryid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderUnitsDTO[] getOrderDeliveryUnitsByDeliveryAndVendor(Long deliveryid, Long vendorid);
	OrderUnitsDTO[] getOrderUnitsRequestByDelivery(Long deliveryid);
	void doDeleteByOrderDeliveries(Long deliveryid, Long[] orderids);
	ImportedNonDeliveredOrderDTO[] getNonDeliveredOrdersByDating(Long deliveryid);
}
