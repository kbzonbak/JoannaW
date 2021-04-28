package bbr.b2b.regional.logistic.locations.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.locations.entities.VendorLocation;
import bbr.b2b.regional.logistic.locations.data.wrappers.VendorLocationW;
import bbr.b2b.regional.logistic.locations.entities.VendorLocationId;
public interface IVendorLocationServer extends IGenericServer<VendorLocation, VendorLocationId, VendorLocationW> {

	VendorLocationW addVendorLocation(VendorLocationW vendorlocation) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorLocationW[] getVendorLocations() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorLocationW updateVendorLocation(VendorLocationW vendorlocation) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
