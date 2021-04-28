package bbr.b2b.logistic.vendors.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.vendors.entities.Vendor;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;

public interface IVendorServer extends IElementServer<Vendor, VendorW> {

	VendorW addVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorW[] getVendors() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorW updateVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorW[] getVendorsByLocationWithDating(Long locationid, LocalDateTime when) throws NotFoundException, OperationFailedException;
	int getCountVendors();
	VendorW getVendorByAsnNumber(String asnnumber) throws NotFoundException, OperationFailedException;
}
