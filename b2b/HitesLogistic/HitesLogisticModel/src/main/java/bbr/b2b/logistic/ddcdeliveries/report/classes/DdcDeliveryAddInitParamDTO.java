package bbr.b2b.logistic.ddcdeliveries.report.classes;

import java.io.Serializable;

public class DdcDeliveryAddInitParamDTO implements Serializable {

	private String vendorcode;
	private Long[] ddcorderids;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long[] getDdcorderids() {
		return ddcorderids;
	}
	public void setDdcorderids(Long[] ddcorderids) {
		this.ddcorderids = ddcorderids;
	}
}
