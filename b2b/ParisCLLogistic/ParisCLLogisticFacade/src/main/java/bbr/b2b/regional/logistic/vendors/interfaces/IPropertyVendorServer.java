package bbr.b2b.regional.logistic.vendors.interfaces;

import java.util.Date;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.vendors.data.wrappers.PropertyVendorW;
import bbr.b2b.regional.logistic.vendors.entities.PropertyVendor;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationSummaryDTO;

public interface IPropertyVendorServer extends IElementServer<PropertyVendor, PropertyVendorW> {

	PropertyVendorW addPropertyVendor(PropertyVendorW propertyvendor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PropertyVendorW[] getPropertyVendors() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PropertyVendorW updatePropertyVendor(PropertyVendorW propertyvendor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	InvoiceVendorRollOutDTO[] getInvoiceVendorRollOutReport(OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	int countInvoiceVendorRollOutReport() throws AccessDeniedException;
	int deleteByIds(Long[] propertyvendorids);
	int deleteByVendorsAndCodes(Long[] vendorids, String[] codes);
	VendorWithoutInvoiceValidationDTO[] getVendorWithoutInvoiceValidationReport(OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	VendorWithoutInvoiceValidationSummaryDTO countVendorWithoutInvoiceValidationReport() throws AccessDeniedException;
	int addVendorsWithoutInvoiceValidation(List<Long> vendorids, String username, Date now);
}
