package bbr.b2b.logistic.vendors.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IVendor extends IElement {

	public String getName();
	public String getCode();
	public String getDni();
	public String getTradename();
	public String getAddress();
	public String getCommune();
	public String getCity();
	public String getGln();
	public Boolean getDomestic();
	public String getEmail();
	public String getPhone();
	public String getCountry();
	public String getInternalcode();
	public Integer getMincorrelative();
	public void setName(String name);
	public void setCode(String code);
	public void setDni(String dni);
	public void setTradename(String tradename);
	public void setAddress(String address);
	public void setCommune(String commune);
	public void setCity(String city);
	public void setGln(String gln);
	public void setDomestic(Boolean domestic);
	public void setEmail(String email);
	public void setPhone(String phone);
	public void setCountry(String country);
	public void setInternalcode(String internalcode);
	public void setMincorrelative(Integer mincorrelative);
}
