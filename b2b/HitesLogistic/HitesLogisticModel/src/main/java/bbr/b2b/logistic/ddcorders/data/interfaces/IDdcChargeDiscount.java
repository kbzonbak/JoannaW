package bbr.b2b.logistic.ddcorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDdcChargeDiscount extends IElement {

	public Boolean getCharge();
	public String getDescription();
	public Integer getVisualorder();
	public Boolean getPercentage();
	public Double getValue();
	public void setCharge(Boolean charge);
	public void setDescription(String description);
	public void setVisualorder(Integer visualorder);
	public void setPercentage(Boolean percentage);
	public void setValue(Double value);
}
