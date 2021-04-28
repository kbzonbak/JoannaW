package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class VendorWithoutInvoiceValidationUploadDTO implements Serializable {

	private Integer line;
	private String vendorcode;
	
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
}
