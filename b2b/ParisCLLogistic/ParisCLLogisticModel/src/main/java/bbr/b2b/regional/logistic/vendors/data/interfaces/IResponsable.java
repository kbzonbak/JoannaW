package bbr.b2b.regional.logistic.vendors.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IResponsable extends IElement {

	public String getCode();
	public String getRut();
	public String getName();
	public String getLastname();
	public String getEmail();
	public String getPhone();
	public String getFax();
	public String getPosition();
	public void setCode(String code);
	public void setRut(String rut);
	public void setName(String name);
	public void setLastname(String lastname);
	public void setEmail(String email);
	public void setPhone(String phone);
	public void setFax(String fax);
	public void setPosition(String position);
}
