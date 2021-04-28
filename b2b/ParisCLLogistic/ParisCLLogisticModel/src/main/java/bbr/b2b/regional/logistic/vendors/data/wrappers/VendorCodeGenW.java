package bbr.b2b.regional.logistic.vendors.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorCodeGen;

public class VendorCodeGenW extends ElementDTO implements IVendorCodeGen {

	private Long number;
	private String alfanumber;
	private String base;

	public Long getNumber(){ 
		return this.number;
	}
	public String getAlfanumber(){ 
		return this.alfanumber;
	}
	public String getBase(){ 
		return this.base;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setAlfanumber(String alfanumber){ 
		this.alfanumber = alfanumber;
	}
	public void setBase(String base){ 
		this.base = base;
	}
}
