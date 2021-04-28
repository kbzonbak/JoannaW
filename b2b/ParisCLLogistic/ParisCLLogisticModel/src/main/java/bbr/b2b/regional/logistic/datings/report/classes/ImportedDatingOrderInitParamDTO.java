package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ImportedDatingOrderInitParamDTO implements Serializable {

	private String locationcode;
	private String vendorcode;
	private Long datingid;
	private Long deliveryid;
	private Long[] orderids;
	
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
	public Long getDatingid() {
		return datingid;
	}
	public void setDatingid(Long datingid) {
		this.datingid = datingid;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long[] getOrderids() {
		return orderids;
	}
	public void setOrderids(Long[] orderids) {
		this.orderids = orderids;
	}
	
}
