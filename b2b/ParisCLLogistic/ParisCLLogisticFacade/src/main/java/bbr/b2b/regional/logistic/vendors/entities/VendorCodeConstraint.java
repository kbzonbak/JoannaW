package bbr.b2b.regional.logistic.vendors.entities;

import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorCodeConstraint;

public class VendorCodeConstraint implements IVendorCodeConstraint {

	private Long id;
	private String name;

	public Long getId(){ 
		return this.id;
	}
	public String getName(){ 
		return this.name;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setName(String name){ 
		this.name = name;
	}
}
