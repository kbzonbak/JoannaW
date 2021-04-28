package bbr.b2b.regional.logistic.couriers.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.couriers.data.interfaces.ICommuneCourier;

public class CommuneCourierW extends ElementDTO implements ICommuneCourier {
	
	private Long id;
	
	private String pariscommune;
	
	private String couriercommune;
	
	private Long courierid;

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
		
}
