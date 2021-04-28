package bbr.b2b.regional.logistic.evaluations.data.classes;

import java.io.Serializable;

public class EvaluationReportInitParamDTO implements Serializable{

	private String vendorcode;
	private String locationcode;
	private Long deliveryid;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	
}
