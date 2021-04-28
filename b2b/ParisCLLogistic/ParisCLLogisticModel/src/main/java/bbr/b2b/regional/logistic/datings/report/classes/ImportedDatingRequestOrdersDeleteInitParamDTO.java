package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ImportedDatingRequestOrdersDeleteInitParamDTO implements Serializable {

	private String locationcode;
	private String vendorcode;
	private Long datingrequestid;
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
	public Long getDatingrequestid() {
		return datingrequestid;
	}
	public void setDatingrequestid(Long datingrequestid) {
		this.datingrequestid = datingrequestid;
	}
	public Long[] getOrderids() {
		return orderids;
	}
	public void setOrderids(Long[] orderids) {
		this.orderids = orderids;
	}
		
}
