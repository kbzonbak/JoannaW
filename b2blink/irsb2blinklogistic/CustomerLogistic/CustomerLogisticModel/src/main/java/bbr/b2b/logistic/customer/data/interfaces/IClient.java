package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IClient extends IElement {

	public String getName();
	public String getIdentity();
	public String getCheck_digit();
	public String getPhone();
	public String getPhone2();
	public String getAddress();
	public String getStreet_number();
	public String getDepartmet_number();
	public String getHouse_number();
	public String getRegion();
	public String getCommune();
	public String getCity();
	public String getLocation();
	public String getObservation();
	public void setName(String name);
	public void setIdentity(String identity);
	public void setCheck_digit(String check_digit);
	public void setPhone(String phone);
	public void setPhone2(String phone2);
	public void setAddress(String address);
	public void setStreet_number(String street_number);
	public void setDepartmet_number(String departmet_number);
	public void setHouse_number(String house_number);
	public void setRegion(String region);
	public void setCommune(String commune);
	public void setCity(String city);
	public void setLocation(String location);
	public void setObservation(String observation);
}
