package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDiscount extends IElement {

	public String getCode();
	public Double getValue();
	public Boolean getPercentage();
	public String getComment();
	public void setCode(String code);
	public void setValue(Double value);
	public void setPercentage(Boolean percentage);
	public void setComment(String comment);
}
