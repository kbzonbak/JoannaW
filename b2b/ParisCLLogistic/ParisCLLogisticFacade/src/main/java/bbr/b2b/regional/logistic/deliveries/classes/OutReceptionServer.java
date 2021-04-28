package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OutReceptionW;
import bbr.b2b.regional.logistic.deliveries.entities.OutReception;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/deliveries/OutReceptionServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OutReceptionServer extends LogisticElementServer<OutReception, OutReceptionW> implements OutReceptionServerLocal {


	@EJB
	VendorServerLocal vendorserver;

	@EJB
	LocationServerLocal locationserver;

	public OutReceptionW addOutReception(OutReceptionW outreception) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutReceptionW) addElement(outreception);
	}
	public OutReceptionW[] getOutReceptions() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutReceptionW[]) getIdentifiables();
	}
	public OutReceptionW updateOutReception(OutReceptionW outreception) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutReceptionW) updateElement(outreception);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OutReception entity, OutReceptionW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OutReception entity, OutReceptionW wrapper) throws OperationFailedException, NotFoundException {
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
		entitylabel = "OutReception";
	}
	@Override
	protected void setEntityname() {
		entityname = "OutReception";
	}
}
