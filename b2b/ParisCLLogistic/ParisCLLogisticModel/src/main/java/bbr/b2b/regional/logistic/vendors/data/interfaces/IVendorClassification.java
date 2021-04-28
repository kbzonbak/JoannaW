package bbr.b2b.regional.logistic.vendors.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IVendorClassification extends IElement {

	public Double getMin();
	public Double getMax();
	public String getName();
	public void setMin(Double min);
	public void setMax(Double max);
	public void setName(String name);
}
