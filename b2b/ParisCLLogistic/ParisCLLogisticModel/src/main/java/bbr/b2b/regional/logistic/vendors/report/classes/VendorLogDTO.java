package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class VendorLogDTO implements Serializable {
	
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
	private Boolean courier;
	private LocalDateTime firstvevcddate;
	private LocalDateTime firstvevpddate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTradename() {
		return tradename;
	}
	public void setTradename(String tradename) {
		this.tradename = tradename;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getDomestic() {
		return domestic;
	}
	public void setDomestic(Boolean domestic) {
		this.domestic = domestic;
	}
	public Integer getLastbulknumber() {
		return lastbulknumber;
	}
	public void setLastbulknumber(Integer lastbulknumber) {
		this.lastbulknumber = lastbulknumber;
	}
	public String getLogisticscode() {
		return logisticscode;
	}
	public void setLogisticscode(String logisticscode) {
		this.logisticscode = logisticscode;
	}
	public Boolean getCourier() {
		return courier;
	}
	public void setCourier(Boolean courier) {
		this.courier = courier;
	}
	public String getGln() {
		return gln;
	}
	public void setGln(String gln) {
		this.gln = gln;
	}
	public LocalDateTime getFirstvevcddate() {
		return firstvevcddate;
	}
	public void setFirstvevcddate(LocalDateTime firstvevcddate) {
		this.firstvevcddate = firstvevcddate;
	}
	public LocalDateTime getFirstvevpddate() {
		return firstvevpddate;
	}
	public void setFirstvevpddate(LocalDateTime firstvevpddate) {
		this.firstvevpddate = firstvevpddate;
	}
	
}