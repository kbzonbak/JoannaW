package bbr.b2b.regional.logistic.vendors.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IVendor extends IElement {

	public String getRut();
	public Integer getNumber();
	public String getName();
	public String getTradename();
	public String getAddress();
	public String getPhone();
	public String getCommune();
	public String getCity();
	public String getEmail();
	public String getFax();
	public String getCode();
	public Boolean getDomestic();
	public Integer getLastbulknumber();
	public String getLogisticscode();
	public void setRut(String rut);
	public void setNumber(Integer number);
	public void setName(String name);
	public void setTradename(String tradename);
	public void setAddress(String address);
	public void setPhone(String phone);
	public void setCommune(String commune);
	public void setCity(String city);
	public void setEmail(String email);
	public void setFax(String fax);
	public void setCode(String code);
	public void setDomestic(Boolean domestic);
	public void setLastbulknumber(Integer lastbulknumber);
	public void setLogisticscode(String logisticscode);
	public String getGln();
	public void setGln(String gln);
	public Date getFirstvevcddate();
	public void setFirstvevcddate(Date firstvevcddate);
	public Date getFirstvevpddate();
	public void setFirstvevpddate(Date firstvevpddate);
	
}
