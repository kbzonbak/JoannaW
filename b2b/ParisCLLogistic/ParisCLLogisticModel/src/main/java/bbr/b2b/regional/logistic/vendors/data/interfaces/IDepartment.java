package bbr.b2b.regional.logistic.vendors.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDepartment extends IElement {

	public String getCode();
	public String getName();
	public void setCode(String code);
	public void setName(String name);
}
