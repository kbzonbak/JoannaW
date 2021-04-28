package bbr.b2b.logistic.items.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.items.data.wrappers.VendorItemW;
import bbr.b2b.logistic.items.entities.VendorItem;
import bbr.b2b.logistic.items.entities.VendorItemId;
import bbr.b2b.logistic.report.classes.VendorItemDataDTO;
public interface IVendorItemServer extends IGenericServer<VendorItem, VendorItemId, VendorItemW> {

	VendorItemW addVendorItem(VendorItemW vendoritem) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemW[] getVendorItems() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemW updateVendorItem(VendorItemW vendoritem) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemDataDTO[] getVendorItemDatasofOrder(Long orderid, Long vendorid) throws OperationFailedException, NotFoundException;
	VendorItemDataDTO[] getVendorItemDataofDirectOrder(Long orderid) throws ServiceException;

}
