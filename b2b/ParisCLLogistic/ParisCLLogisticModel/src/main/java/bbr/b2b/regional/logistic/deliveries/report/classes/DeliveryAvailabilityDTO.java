package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DeliveryAvailabilityDTO implements Serializable{
	
	private Long deliveryid;
	private Long ordernumber;
	private String parissku;
	private String localcode;
	private String atccode;
	private double availableunits;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getParissku() {
		return parissku;
	}
	public void setParissku(String parissku) {
		this.parissku = parissku;
	}
	public String getLocalcode() {
		return localcode;
	}
	public void setLocalcode(String localcode) {
		this.localcode = localcode;
	}
	public String getAtccode() {
		return atccode;
	}
	public void setAtccode(String atccode) {
		this.atccode = atccode;
	}
	public double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(double availableunits) {
		this.availableunits = availableunits;
	}

}
