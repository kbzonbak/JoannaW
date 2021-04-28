package bbr.b2b.regional.logistic.buyorders.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ItemAtcW;
import bbr.b2b.regional.logistic.buyorders.entities.ItemAtc;
import bbr.b2b.regional.logistic.buyorders.entities.ItemAtcId;
import bbr.b2b.regional.logistic.buyorders.report.classes.ItemAtcDTO;

public interface IItemAtcServer extends IGenericServer<ItemAtc, ItemAtcId, ItemAtcW> {

	ItemAtcW addItemAtc(ItemAtcW itematc) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemAtcW[] getItemAtcs() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ItemAtcW updateItemAtc(ItemAtcW itematc) throws AccessDeniedException, OperationFailedException, NotFoundException;
	List<ItemAtcDTO> getItemDataByATCCodes(List<String> atccodes);
	List<ItemAtcDTO> getItemDataByOrderAndATCCodes(Long orderid, List<String> atccodes);
	List<ItemAtcDTO> getItemDataByOrderAndSKUs(Long orderid, List<String> itemcodes);
	List<ItemAtcDTO> getItemDataByDelivery(Long deliveryid);
	List<Long> getItemIdsByATCCode(String atccode);
	
}
