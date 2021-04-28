package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DatingRequestInitParamDTO implements Serializable {

	private DatingRequestDataDTO datingrequestdata;
	private Long[] dvrorderids;
	private String vendorcode;
	private Long dvrdeliveryid; // Se llama si isByDelivery = true

	public DatingRequestDataDTO getDatingrequestdata() {
		return datingrequestdata;
	}

	public void setDatingrequestdata(DatingRequestDataDTO datingrequestdata) {
		this.datingrequestdata = datingrequestdata;
	}

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
