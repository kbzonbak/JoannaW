package bbr.b2b.regional.logistic.vendors.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.vendors.entities.VendorClassification;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorClassificationW;

public interface IVendorClassificationServer extends IElementServer<VendorClassification, VendorClassificationW> {

	VendorClassificationW addVendorClassification(VendorClassificationW vendorclassification) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorClassificationW[] getVendorClassifications() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorClassificationW updateVendorClassification(VendorClassificationW vendorclassification) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
