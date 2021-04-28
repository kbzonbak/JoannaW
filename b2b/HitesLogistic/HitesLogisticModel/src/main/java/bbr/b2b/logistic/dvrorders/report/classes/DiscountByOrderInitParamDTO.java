package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DiscountByOrderInitParamDTO implements Serializable {

	private Long dvrorderid;
	private String vendorcode;

	public Long getDvrorderid() {
		return dvrorderid;
	}

	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

}
