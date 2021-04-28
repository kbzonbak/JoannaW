package bbr.b2b.logistic.customer.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.customer.entities.Vendor;

@Stateless(name = "servers/customer/VendorServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorServer extends LogisticElementServer<Vendor, VendorW> implements VendorServerLocal {


	public VendorW addVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW) addElement(vendor);
	}
	public VendorW[] getVendors() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW[]) getIdentifiables();
	}
	public VendorW updateVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW) updateElement(vendor);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Vendor entity, VendorW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Vendor entity, VendorW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Vendor";
	}
	@Override
	protected void setEntityname() {
		entityname = "Vendor";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
