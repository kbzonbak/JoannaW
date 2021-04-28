package bbr.b2b.regional.logistic.vendors.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendor;

public class Vendor implements IVendor {

	private Long id;
	private String rut;
	private Integer number;
	private String name;
	private String tradename;
	private String address;
	private String phone;
	private String commune;
	private String city;
	private String email;
	private String fax;
	private String code;
	private Boolean domestic;
	private Integer lastbulknumber;
	private String logisticscode;
	private String gln;
	private Date firstvevcddate;
	private Date firstvevpddate;

	public Long getId(){ 
		return this.id;
	}
	public String getRut(){ 
		return this.rut;
	}
	public Integer getNumber(){ 
		return this.number;
	}
	public String getName(){ 
		return this.name;
	}
	public String getTradename(){ 
		return this.tradename;
	}
	public String getAddress(){ 
		return this.address;
	}
	public String getPhone(){ 
		return this.phone;
	}
	public String getCommune(){ 
		return this.commune;
	}
	public String getCity(){ 
		return this.city;
	}
	public String getEmail(){ 
		return this.email;
	}
	public String getFax(){ 
		return this.fax;
	}
	public String getCode(){ 
		return this.code;
	}
	public Boolean getDomestic(){ 
		return this.domestic;
	}
	public Integer getLastbulknumber(){ 
		return this.lastbulknumber;
	}
	public String getLogisticscode(){ 
		return this.logisticscode;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setRut(String rut){ 
		this.rut = rut;
	}
	public void setNumber(Integer number){ 
		this.number = number;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setTradename(String tradename){ 
		this.tradename = tradename;
	}
	public void setAddress(String address){ 
		this.address = address;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setCommune(String commune){ 
		this.commune = commune;
	}
	public void setCity(String city){ 
		this.city = city;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
	public void setFax(String fax){ 
		this.fax = fax;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDomestic(Boolean domestic){ 
		this.domestic = domestic;
	}
	public void setLastbulknumber(Integer lastbulknumber){ 
		this.lastbulknumber = lastbulknumber;
	}
	public void setLogisticscode(String logisticscode){ 
		this.logisticscode = logisticscode;
	}
	public String getGln() {
		return gln;
	}
	public void setGln(String gln) {
		this.gln = gln;
	}
	public Date getFirstvevcddate() {
		return firstvevcddate;
	}
	public void setFirstvevcddate(Date firstvevcddate) {
		this.firstvevcddate = firstvevcddate;
	}
	public Date getFirstvevpddate() {
		return firstvevpddate;
	}
	public void setFirstvevpddate(Date firstvevpddate) {
		this.firstvevpddate = firstvevpddate;
	}
	
}
