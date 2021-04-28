package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IHomologation extends IElement {

	public String getCode();
	public String getName();
	public String getValue();
	public void setCode(String code);
	public void setName(String name);
	public void setValue(String value);
}
