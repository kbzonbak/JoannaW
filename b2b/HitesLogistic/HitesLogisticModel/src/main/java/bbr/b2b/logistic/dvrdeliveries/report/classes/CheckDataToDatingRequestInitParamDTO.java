package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class CheckDataToDatingRequestInitParamDTO implements Serializable {

	private Long[] dvrorderids;
	private String vendorcode;
	private Long dvrdeliveryid; // Se llama si isByDelivery = true

	public Long[] getDvrorderids() {
		return dvrorderids;
	}

	public void setDvrorderids(Long[] dvrorderids) {
		this.dvrorderids = dvrorderids;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

}
