package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class UpdateDODeliveryDispatcherEmail implements Serializable {

	private String vendorcode;
	private Long dodeliveryid;
	private String dispatcheremail;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}
	public String getDispatcheremail() {
		return dispatcheremail;
	}
	public void setDispatcheremail(String dispatcheremail) {
		this.dispatcheremail = dispatcheremail;
	}	
}
