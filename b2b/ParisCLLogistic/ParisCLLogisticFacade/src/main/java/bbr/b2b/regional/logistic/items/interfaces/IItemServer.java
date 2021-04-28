package bbr.b2b.regional.logistic.items.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.report.classes.PendingItemDTO;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemDataW;

public interface IItemServer extends IElementServer<Item, ItemW> {

	ItemW addItem(ItemW item) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemW[] getItems() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemW updateItem(ItemW item) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemW[] getItemsOfOrder(Long ordernumber) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemDataW[] getItemsOfVendorAndVeV(Long vendorid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	List<String> getDeliveryItemsByOrders(Long[] orderids);
	PendingItemDTO[] getPendingItemsData(Long[] orderids, List<String> skus);
	ItemW[] getItemsByIds(Long[] ids) throws ServiceException;
	List<String> getBySKUs(List<String> codeList);
	VendorItemDataW[] getItemsByDelivery(Long deliveryid) throws OperationFailedException, NotFoundException;
	ItemW[] getItemsByDirectOrder(Long directorderid) throws OperationFailedException, NotFoundException;
}
