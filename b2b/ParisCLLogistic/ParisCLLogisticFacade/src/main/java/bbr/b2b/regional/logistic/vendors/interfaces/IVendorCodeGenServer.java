package bbr.b2b.regional.logistic.vendors.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.vendors.entities.VendorCodeGen;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorCodeGenW;

public interface IVendorCodeGenServer extends IElementServer<VendorCodeGen, VendorCodeGenW> {

	VendorCodeGenW addVendorCodeGen(VendorCodeGenW vendorcodegen) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorCodeGenW[] getVendorCodeGens() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorCodeGenW updateVendorCodeGen(VendorCodeGenW vendorcodegen) throws AccessDeniedException, OperationFailedException, NotFoundException;
	String getNextCode() throws ServiceException;
}
