package bbr.b2b.regional.logistic.vendors.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.vendors.entities.VendorCodeGen;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorCodeGenW;

@Stateless(name = "servers/vendors/VendorCodeGenServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorCodeGenServer extends LogisticElementServer<VendorCodeGen, VendorCodeGenW> implements VendorCodeGenServerLocal {


	public VendorCodeGenW addVendorCodeGen(VendorCodeGenW vendorcodegen) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorCodeGenW) addElement(vendorcodegen);
	}
	public VendorCodeGenW[] getVendorCodeGens() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorCodeGenW[]) getIdentifiables();
	}
	public VendorCodeGenW updateVendorCodeGen(VendorCodeGenW vendorcodegen) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorCodeGenW) updateElement(vendorcodegen);
	}

	@Override
	protected void copyRelationsEntityToWrapper(VendorCodeGen entity, VendorCodeGenW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(VendorCodeGen entity, VendorCodeGenW wrapper) throws OperationFailedException, NotFoundException {
	}

	public String getNextCode() throws ServiceException {
		VendorCodeGenW codegen = null;

		codegen = getByPropertyAsSingleResult("base", RegionalLogisticConstants.getInstance().getVendorCodeBase().toString());
 
        Long nextnumber = codegen.getNumber() + 1;
        String nextA = getCode(nextnumber.intValue(), Integer.parseInt(codegen.getBase()), RegionalLogisticConstants.getInstance().getVendorCodeLength().intValue());
        codegen.setNumber(nextnumber);
        codegen.setAlfanumber(nextA);
        updateElement(codegen);        
        return new String(nextA);        
	}
	
	private String getCode(int number, int base, int length) {
        String result = getCode(number, base);
        int prefixlength = length - result.length();
        if (prefixlength > 0) {
        	String prefix = "000".substring(0, prefixlength);
        	result = prefix + result;
        }
        return result;
	}
	
	private String getCode(int number, int base) {
        // Convertir un N°mero en base "base" (hasta base 36)
        String result = Integer.toString(number, base).toUpperCase();
        return result;
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorCodeGen";
	}
	@Override
	protected void setEntityname() {
		entityname = "VendorCodeGen";
	}
}
