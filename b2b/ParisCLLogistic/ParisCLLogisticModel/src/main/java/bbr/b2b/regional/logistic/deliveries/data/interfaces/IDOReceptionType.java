package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDOReceptionType extends IElement {

	public String getCode();
	public String getName();
	public void setCode(String code);
	public void setName(String name);
}
