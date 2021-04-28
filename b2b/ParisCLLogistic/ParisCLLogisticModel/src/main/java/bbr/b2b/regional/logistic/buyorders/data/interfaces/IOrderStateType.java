package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrderStateType extends IElement {

	public String getCode();
	public String getName();
	public Boolean getValid();
	public Boolean getShowable();
	public void setCode(String code);
	public void setName(String name);
	public void setValid(Boolean valid);
	public void setShowable(Boolean showable);
}
