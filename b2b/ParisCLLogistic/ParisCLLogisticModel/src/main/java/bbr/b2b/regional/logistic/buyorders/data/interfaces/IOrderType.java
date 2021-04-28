package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrderType extends IElement {

	public String getCode();
	public String getName();
	public Boolean getGroupable();
	public void setCode(String code);
	public void setName(String name);
	public void setGroupable(Boolean groupable);
}
