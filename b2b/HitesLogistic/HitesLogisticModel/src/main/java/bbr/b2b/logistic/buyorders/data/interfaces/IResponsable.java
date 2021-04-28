package bbr.b2b.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IResponsable extends IElement {

	public String getCode();
	public String getName();
	public String getEmail();
	public void setCode(String code);
	public void setName(String name);
	public void setEmail(String email);
}
