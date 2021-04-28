package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class DeleteDatingInitParamDTO implements Serializable {
	
	private Long deliveryid;
	private String locationcode;
	private String reason;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}	
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}	
}
