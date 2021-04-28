package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class UploadPackingListInitParamDTO implements Serializable {

	private String vendorcode;
	private Long dvrdeliveryid;
	private String filename;

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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
