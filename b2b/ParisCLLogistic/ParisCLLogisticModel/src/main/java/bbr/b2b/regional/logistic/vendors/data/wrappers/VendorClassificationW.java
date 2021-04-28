package bbr.b2b.regional.logistic.vendors.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorClassification;

public class VendorClassificationW extends ElementDTO implements IVendorClassification {

	private Double min;
	private Double max;
	private String name;

	public Double getMin(){ 
		return this.min;
	}
	public Double getMax(){ 
		return this.max;
	}
	public String getName(){ 
		return this.name;
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
