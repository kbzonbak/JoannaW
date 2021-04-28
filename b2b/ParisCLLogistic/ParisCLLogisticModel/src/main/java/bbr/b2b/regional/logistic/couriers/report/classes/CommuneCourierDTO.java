package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

public class CommuneCourierDTO implements Serializable {
	
	private Long id;
	private String pariscommune;
	private String couriercommune;
	private Long courierid;
	private String courier;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPariscommune() {
		return pariscommune;
	}
	public void setPariscommune(String pariscommune) {
		this.pariscommune = pariscommune;
	}
	public String getCouriercommune() {
		return couriercommune;
	}
	public void setCouriercommune(String couriercommune) {
		this.couriercommune = couriercommune;
	}
	public Long getCourierid() {
		return courierid;
	}
	public void setCourierid(Long courierid) {
		this.courierid = courierid;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	
}
