package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class DeliveryCourierChilexpressInitParamDTO extends UserDataInitParamDTO {

	private Long[] dodeliveryids;
	private String vendorcode;
	
	public Long[] getDodeliveryids() {
		return dodeliveryids;
	}
	public void setDodeliveryids(Long[] dodeliveryids) {
		this.dodeliveryids = dodeliveryids;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
}
