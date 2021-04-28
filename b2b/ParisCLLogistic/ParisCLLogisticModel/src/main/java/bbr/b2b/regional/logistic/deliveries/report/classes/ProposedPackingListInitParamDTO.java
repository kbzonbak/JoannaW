package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class ProposedPackingListInitParamDTO implements Serializable {

	private Long deliveryid;
	private String vendorcode;
	private Long locationdid;
	private boolean byinnerpack;
	
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
	public boolean isByinnerpack() {
		return byinnerpack;
	}
	public void setByinnerpack(boolean byinnerpack) {
		this.byinnerpack = byinnerpack;
	}	
}
