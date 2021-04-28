package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IVendor extends IElement {

	public String getCode();
	public String getName();
	public String getRut();
	public String getDv();
	public String getGln();
	public String getAddress();
	public String getPhone();
	public String getEmail();
	public void setCode(String code);
	public void setName(String name);
	public void setRut(String rut);
	public void setDv(String dv);
	public void setGln(String gln);
	public void setAddress(String address);
	public void setPhone(String phone);
	public void setEmail(String email);
}
