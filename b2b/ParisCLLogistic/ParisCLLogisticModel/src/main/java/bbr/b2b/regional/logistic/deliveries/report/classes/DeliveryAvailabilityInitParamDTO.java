package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DeliveryAvailabilityInitParamDTO implements Serializable{

	private String vendorcode;
	private Long deliveryid;
	private DeliveryAvailabilityDTO[] deliveryavailabilities;
	
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
	public DeliveryAvailabilityDTO[] getDeliveryavailabilities() {
		return deliveryavailabilities;
	}
	public void setDeliveryavailabilities(DeliveryAvailabilityDTO[] deliveryavailabilities) {
		this.deliveryavailabilities = deliveryavailabilities;
	}
	
}
