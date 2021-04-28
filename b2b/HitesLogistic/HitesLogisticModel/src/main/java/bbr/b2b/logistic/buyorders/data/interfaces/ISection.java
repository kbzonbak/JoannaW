package bbr.b2b.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ISection extends IElement {

	public String getName();
	public String getCode();
	public void setName(String name);
	public void setCode(String code);
}
