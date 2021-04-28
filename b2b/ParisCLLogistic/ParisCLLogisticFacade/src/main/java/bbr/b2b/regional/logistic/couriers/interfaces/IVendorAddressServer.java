package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.VendorAddressW;
import bbr.b2b.regional.logistic.couriers.entities.VendorAddress;

public interface IVendorAddressServer extends IElementServer<VendorAddress, VendorAddressW> {
	
	VendorAddressW addVendorAddress(VendorAddressW vendoraddress) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorAddressW[] getVendorAddresses() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorAddressW updateVendorAddress(VendorAddressW vendoraddress) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int deleteVendorAddress(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
