package bbr.b2b.regional.logistic.items.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.items.data.wrappers.VendorItemW;
import bbr.b2b.regional.logistic.items.entities.VendorItem;
import bbr.b2b.regional.logistic.items.entities.VendorItemId;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemDataW;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemReportDataDTO;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemVevReportDataDTO;
public interface IVendorItemServer extends IGenericServer<VendorItem, VendorItemId, VendorItemW> {

	VendorItemW addVendorItem(VendorItemW vendoritem) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemW[] getVendorItems() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemW updateVendorItem(VendorItemW vendoritem) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemDataW[] getVendorItemDataofOrder(Long orderid) throws ServiceException;	
	VendorItemDataW[] getVendorItemDataofDirectOrder(Long orderid) throws ServiceException;
	VendorItemReportDataDTO[] getVendorItemsByInternalCode(Long vendorid, String itemsku, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemReportDataDTO[] getVendorItemsByVendorItemCode(Long vendorid, String vendoritemcode, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemReportDataDTO[] getVendorItemsByName(Long vendorid, String description, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemReportDataDTO[] getVendorItemsByOrderNumber(Long vendorid, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getVendorItemsCountByInternalCode(Long vendorid, String itemsku) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getVendorItemsCountByVendorItemCode(Long vendorid, String vendoritemcode) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getVendorItemsCountByName(Long vendorid, String description) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getVendorItemsCountByOrderNumber(Long vendorid, Long ordernumber) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorItemVevReportDataDTO getVendorItemByVendorAndSku(Long vendorid, String itemcode) throws ServiceException;
}
