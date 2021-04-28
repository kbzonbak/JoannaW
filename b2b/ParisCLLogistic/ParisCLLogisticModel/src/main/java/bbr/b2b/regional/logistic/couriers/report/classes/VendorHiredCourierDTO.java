package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

public class VendorHiredCourierDTO implements Serializable{
	
	private String code;

	private String name;

	private String description;

	private String clientcode;
	
	private String user;
	
	private String password;
	
	private Long courierid;
	
	private Long vendorid;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClientcode() {
		return clientcode;
	}

	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCourierid() {
		return courierid;
	}

	public void setCourierid(Long courierid) {
		this.courierid = courierid;
	}

	public Long getVendorid() {
		return vendorid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
}
