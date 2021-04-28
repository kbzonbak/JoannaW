package bbr.b2b.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrderType extends IElement {

	public String getCode();
	public String getDescription();
	public void setCode(String code);
	public void setDescription(String description);
}
