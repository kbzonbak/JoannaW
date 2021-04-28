package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DvrDeliveryDeleteInitParamDTO implements Serializable {

	private Long dvrdeliveryid;
	private String vendorcode;

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

}
