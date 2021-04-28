package bbr.b2b.regional.logistic.couriers.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.VendorAddressW;
import bbr.b2b.regional.logistic.couriers.entities.HiredCourier;
import bbr.b2b.regional.logistic.couriers.entities.HiredCourierId;
import bbr.b2b.regional.logistic.couriers.entities.VendorAddress;

@Stateless(name = "servers/couriers/VendorAddressServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorAddressServer extends LogisticElementServer<VendorAddress, VendorAddressW> implements VendorAddressServerLocal{
	
	@EJB
	HiredCourierServerLocal hiredcourierserver;

	@Override
	protected void copyRelationsEntityToWrapper(VendorAddress entity, VendorAddressW wrapper) throws OperationFailedException, NotFoundException {
		wrapper.setVendorid(entity.getHiredcourier() != null ? new Long(entity.getHiredcourier().getId().getVendorid()) : new Long(0));
		wrapper.setCourierid(entity.getHiredcourier() != null ? new Long(entity.getHiredcourier().getId().getCourierid()) : new Long(0));		
	}

	@Override
	protected void copyRelationsWrapperToEntity(VendorAddress entity, VendorAddressW wrapper) throws OperationFailedException, NotFoundException {
		if ((wrapper.getVendorid() != null && wrapper.getVendorid() > 0) && (wrapper.getCourierid() != null && wrapper.getCourierid() > 0)) {
			HiredCourierId key = new HiredCourierId();
			key.setVendorid(wrapper.getVendorid());
			key.setCourierid(wrapper.getCourierid());
			HiredCourier hiredcourier = hiredcourierserver.getReferenceById(key);
			if (hiredcourier != null) { 
				entity.setHiredcourier(hiredcourier);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorAddress";
		
	}

	@Override
	protected void setEntityname() {
		entityname = "VendorAddress";
		
	}
	
	public VendorAddressW addVendorAddress(VendorAddressW vendoraddress) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorAddressW) addElement(vendoraddress);
	}
	public VendorAddressW[] getVendorAddresses() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorAddressW[]) getIdentifiables();
	}
	public VendorAddressW updateVendorAddress(VendorAddressW vendoraddress) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorAddressW) updateElement(vendoraddress);
	}
	public int deleteVendorAddress(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return deleteByProperties(pven);
	}

}
