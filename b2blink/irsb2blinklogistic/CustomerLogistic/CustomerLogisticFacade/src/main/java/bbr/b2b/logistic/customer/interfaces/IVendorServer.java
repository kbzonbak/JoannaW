package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.Vendor;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;

public interface IVendorServer extends IElementServer<Vendor, VendorW> {

	VendorW addVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorW[] getVendors() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorW updateVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
