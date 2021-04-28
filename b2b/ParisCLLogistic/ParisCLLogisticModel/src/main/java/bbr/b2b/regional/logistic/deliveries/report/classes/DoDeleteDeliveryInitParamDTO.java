package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DoDeleteDeliveryInitParamDTO implements Serializable {

	private Long deliveryid;
	private String vendorcode;
	private Long locationdid;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getLocationdid() {
		return locationdid;
	}
	public void setLocationdid(Long locationdid) {
		this.locationdid = locationdid;
	}	
}
