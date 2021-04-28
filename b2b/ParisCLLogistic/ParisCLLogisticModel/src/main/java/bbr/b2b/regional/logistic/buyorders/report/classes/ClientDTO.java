package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class ClientDTO implements Serializable{

	private Long id;
	private String rut;
	private String name;
	private String address;
	private String phone;
	private String commune;
	private String city;
	private String region;
	private String housenumber;
	private String deptnumber;
	private String roadnumber;
	private String comment;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getHousenumber() {
		return housenumber;
	}
	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}
	public String getDeptnumber() {
		return deptnumber;
	}
	public void setDeptnumber(String deptnumber) {
		this.deptnumber = deptnumber;
	}
	public String getRoadnumber() {
		return roadnumber;
	}
	public void setRoadnumber(String roadnumber) {
		this.roadnumber = roadnumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
