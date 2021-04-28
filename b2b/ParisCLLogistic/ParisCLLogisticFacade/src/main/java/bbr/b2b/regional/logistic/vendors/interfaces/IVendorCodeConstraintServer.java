package bbr.b2b.regional.logistic.vendors.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.vendors.entities.VendorCodeConstraint;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorCodeConstraintW;

public interface IVendorCodeConstraintServer extends IElementServer<VendorCodeConstraint, VendorCodeConstraintW> {

	VendorCodeConstraintW addVendorCodeConstraint(VendorCodeConstraintW vendorcodeconstraint) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorCodeConstraintW[] getVendorCodeConstraints() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorCodeConstraintW updateVendorCodeConstraint(VendorCodeConstraintW vendorcodeconstraint) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
