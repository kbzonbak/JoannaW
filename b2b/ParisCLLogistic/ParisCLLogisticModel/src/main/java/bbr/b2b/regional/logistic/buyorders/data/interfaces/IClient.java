package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IClient extends IElement {

	public String getRut();
	public String getName();
	public String getAddress();
	public String getPhone();
	public String getCommune();
	public String getCity();
	public String getRegion();
	public String getHousenumber();
	public String getDeptnumber();
	public String getRoadnumber();
	public String getComment();
	public void setRut(String rut);
	public void setName(String name);
	public void setAddress(String address);
	public void setPhone(String phone);
	public void setCommune(String commune);
	public void setCity(String city);
	public void setRegion(String region);
	public void setHousenumber(String housenumber);
	public void setDeptnumber(String deptnumber);
	public void setRoadnumber(String roadnumber);
	public void setComment(String comment);
}
