package bbr.b2b.logistic.locations.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ILocation extends IElement {

	public String getCode();
	public String getName();
	public String getAddress();
	public String getCommune();
	public String getCity();
	public String getPhone();
	public Boolean getWarehouse();
	public Integer getPriority();
	public String getEmail();
	public String getGln();
	public String getManagername();
	public String getManageremail();
	public void setCode(String code);
	public void setName(String name);
	public void setAddress(String address);
	public void setCommune(String commune);
	public void setCity(String city);
	public void setPhone(String phone);
	public void setWarehouse(Boolean warehouse);
	public void setPriority(Integer priority);
	public void setEmail(String email);
	public void setGln(String gln);
	public void setManagername(String managername);
	public void setManageremail(String manageremail);
}
