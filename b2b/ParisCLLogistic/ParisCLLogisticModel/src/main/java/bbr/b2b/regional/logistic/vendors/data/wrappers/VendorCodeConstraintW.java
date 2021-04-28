package bbr.b2b.regional.logistic.vendors.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorCodeConstraint;

public class VendorCodeConstraintW extends ElementDTO implements IVendorCodeConstraint {

	private String name;

	public String getName(){ 
		return this.name;
	}
	public void setName(String name){ 
		this.name = name;
	}
}
