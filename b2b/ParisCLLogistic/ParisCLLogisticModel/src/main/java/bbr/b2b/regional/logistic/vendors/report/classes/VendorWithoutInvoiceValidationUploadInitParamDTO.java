package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class VendorWithoutInvoiceValidationUploadInitParamDTO implements Serializable {

	private String filename;
	private Long userid;
	private String username;
	private VendorWithoutInvoiceValidationUploadDTO[] vendordata;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public VendorWithoutInvoiceValidationUploadDTO[] getVendordata() {
		return vendordata;
	}

	public void setVendordata(VendorWithoutInvoiceValidationUploadDTO[] vendordata) {
		this.vendordata = vendordata;
	}
}
