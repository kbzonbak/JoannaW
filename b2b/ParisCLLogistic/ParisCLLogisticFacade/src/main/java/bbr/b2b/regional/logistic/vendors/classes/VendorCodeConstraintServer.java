package bbr.b2b.regional.logistic.vendors.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.vendors.entities.VendorCodeConstraint;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorCodeConstraintW;

@Stateless(name = "servers/vendors/VendorCodeConstraintServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorCodeConstraintServer extends LogisticElementServer<VendorCodeConstraint, VendorCodeConstraintW> implements VendorCodeConstraintServerLocal {


	public VendorCodeConstraintW addVendorCodeConstraint(VendorCodeConstraintW vendorcodeconstraint) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorCodeConstraintW) addElement(vendorcodeconstraint);
	}
	public VendorCodeConstraintW[] getVendorCodeConstraints() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorCodeConstraintW[]) getIdentifiables();
	}
	public VendorCodeConstraintW updateVendorCodeConstraint(VendorCodeConstraintW vendorcodeconstraint) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorCodeConstraintW) updateElement(vendorcodeconstraint);
	}

	@Override
	protected void copyRelationsEntityToWrapper(VendorCodeConstraint entity, VendorCodeConstraintW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(VendorCodeConstraint entity, VendorCodeConstraintW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorCodeConstraint";
	}
	@Override
	protected void setEntityname() {
		entityname = "VendorCodeConstraint";
	}
}
