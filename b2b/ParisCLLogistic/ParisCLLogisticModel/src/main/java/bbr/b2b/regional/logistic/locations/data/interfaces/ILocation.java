package bbr.b2b.regional.logistic.locations.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ILocation extends IElement {

	public String getCode();
	public String getName();
	public String getShortname();
	public String getAddress();
	public String getEmail();
	public Integer getPriority();
	public String getPhone();
	public String getFax();
	public String getCommune();
	public String getCity();
	public Boolean getWarehouse();
	public void setCode(String code);
	public void setName(String name);
	public void setShortname(String shortname);
	public void setAddress(String address);
	public void setEmail(String email);
	public void setPriority(Integer priority);
	public void setPhone(String phone);
	public void setFax(String fax);
	public void setCommune(String commune);
	public void setCity(String city);
	public void setWarehouse(Boolean warehouse);
}
