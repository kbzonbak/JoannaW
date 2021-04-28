package bbr.b2b.regional.logistic.vendors.entities;

import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorClassification;

public class VendorClassification implements IVendorClassification {

	private Long id;
	private Double min;
	private Double max;
	private String name;

	public Long getId(){ 
		return this.id;
	}
	public Double getMin(){ 
		return this.min;
	}
	public Double getMax(){ 
		return this.max;
	}
	public String getName(){ 
		return this.name;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setMin(Double min){ 
		this.min = min;
	}
	public void setMax(Double max){ 
		this.max = max;
	}
	public void setName(String name){ 
		this.name = name;
	}
}
