package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DeliveryDetailInitParamDTO implements Serializable{

	private Long deliveryid;
	private String vendorcode;
	private String locationdcode;
	
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
	public String getLocationdcode() {
		return locationdcode;
	}
	public void setLocationdcode(String locationdcode) {
		this.locationdcode = locationdcode;
	}
	
	
}
