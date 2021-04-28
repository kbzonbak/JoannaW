package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IVendorAddress extends IElement {

	public boolean getVisible();
	public void setVisible(boolean visible);
	public Date getUpdatedate();
	public void setUpdatedate(Date updatedate);
	public String getDescription();
	public void setDescription(String description);
	public String getCommune();
	public void setCommune(String commune);
	public String getStreet();
	public void setStreet(String street);
	public String getNumber();
	public void setNumber(String number);
	public String getComment();
	public void setComment(String comment);
	public String getEmail();
	public void setEmail(String email);
	public String getPhone1();
	public void setPhone1(String phone1);
	public String getPhone2();
	public void setPhone2(String phone2);
	public String getUser1();
	public void setUser1(String user1);	
	public Double getWeight();
	public void setWeight(Double weight);
	public Double getLength();
	public void setLength(Double length);
	public Double getHeight();
	public void setHeight(Double height);
	public Double getWidth();
	public void setWidth(Double width);
	
}
