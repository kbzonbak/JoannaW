package bbr.b2b.regional.logistic.vendors.entities;

import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorCodeGen;

public class VendorCodeGen implements IVendorCodeGen {

	private Long id;
	private Long number;
	private String alfanumber;
	private String base;

	public Long getId(){ 
		return this.id;
	}
	public Long getNumber(){ 
		return this.number;
	}
	public String getAlfanumber(){ 
		return this.alfanumber;
	}
	public String getBase(){ 
		return this.base;
	}
	public void setId(Long id){ 
		this.id = id;
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
