package bbr.b2b.regional.logistic.locations.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.locations.data.wrappers.VendorLocationW;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.locations.entities.VendorLocation;
import bbr.b2b.regional.logistic.locations.entities.VendorLocationId;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/locations/VendorLocationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorLocationServer extends BaseLogisticEJB3Server<VendorLocation, VendorLocationId, VendorLocationW> implements VendorLocationServerLocal {


	@EJB
	VendorServerLocal vendorserver;

	@EJB
	LocationServerLocal locationserver;

	public VendorLocationW addVendorLocation(VendorLocationW vendorlocation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorLocationW) addIdentifiable(vendorlocation);
	}
	public VendorLocationW[] getVendorLocations() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorLocationW[]) getIdentifiables();
	}
	public VendorLocationW updateVendorLocation(VendorLocationW vendorlocation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorLocationW) updateIdentifiable(vendorlocation);
	}

	@Override
	protected void copyRelationsEntityToWrapper(VendorLocation entity, VendorLocationW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(VendorLocation entity, VendorLocationW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorLocation";
	}
	@Override
	protected void setEntityname() {
		entityname = "VendorLocation";
	}
}
