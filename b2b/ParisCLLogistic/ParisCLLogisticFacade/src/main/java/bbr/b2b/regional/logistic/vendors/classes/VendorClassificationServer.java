package bbr.b2b.regional.logistic.vendors.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.vendors.entities.VendorClassification;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorClassificationW;

@Stateless(name = "servers/vendors/VendorClassificationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorClassificationServer extends LogisticElementServer<VendorClassification, VendorClassificationW> implements VendorClassificationServerLocal {


	public VendorClassificationW addVendorClassification(VendorClassificationW vendorclassification) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorClassificationW) addElement(vendorclassification);
	}
	public VendorClassificationW[] getVendorClassifications() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorClassificationW[]) getIdentifiables();
	}
	public VendorClassificationW updateVendorClassification(VendorClassificationW vendorclassification) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorClassificationW) updateElement(vendorclassification);
	}

	@Override
	protected void copyRelationsEntityToWrapper(VendorClassification entity, VendorClassificationW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(VendorClassification entity, VendorClassificationW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorClassification";
	}
	@Override
	protected void setEntityname() {
		entityname = "VendorClassification";
	}
}
