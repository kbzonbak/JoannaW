package bbr.b2b.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IClient extends IElement {

	public String getDni();
	public String getName();
	public String getAddress();
	public String getPhone();
	public String getEmail();
	public String getCity();
	public String getCommune();
	public String getComment();
	public String getStreetname();
	public String getStreetnumber();
	public String getDeparmentnumber();
	public void setDni(String dni);
	public void setName(String name);
	public void setAddress(String address);
	public void setPhone(String phone);
	public void setEmail(String email);
	public void setCity(String city);
	public void setCommune(String commune);
	public void setComment(String comment);
	public void setStreetname(String streetname);
	public void setStreetnumber(String streetnumber);
	public void setDeparmentnumber(String deparmentnumber);
}
