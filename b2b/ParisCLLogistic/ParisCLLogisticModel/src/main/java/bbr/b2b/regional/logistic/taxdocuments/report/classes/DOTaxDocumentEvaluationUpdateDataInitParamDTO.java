package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOTaxDocumentEvaluationUpdateDataInitParamDTO implements Serializable {
	
	private String vendorcode;
	private String username;
	private String usertype;
	private String excelfilename;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getExcelfilename() {
		return excelfilename;
	}
	public void setExcelfilename(String excelfilename) {
		this.excelfilename = excelfilename;
	}
}
