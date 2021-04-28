package bbr.b2b.regional.logistic.evaluations.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IReceptionError extends IElement {

	public String getCode();
	public String getName();
	public void setCode(String code);
	public void setName(String name);
}
