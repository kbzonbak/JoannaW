package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ImportedDatingInitParamDTO implements Serializable {

	private String locationcode;
	private String vendorcode;
	private Long deliveryid;
	
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	
}
