package bbr.b2b.regional.logistic.couriers.entities;

import bbr.b2b.regional.logistic.couriers.data.interfaces.ICommuneCourier;

public class CommuneCourier implements ICommuneCourier {
	
	private Long id;
	private String pariscommune;
	private String couriercommune;
	private Courier courier;

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

	public Courier getCourier() {
		return courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

}
