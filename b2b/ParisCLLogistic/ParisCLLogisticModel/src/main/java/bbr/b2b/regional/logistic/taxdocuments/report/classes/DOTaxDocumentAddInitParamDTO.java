package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOTaxDocumentAddInitParamDTO implements Serializable{

	private String vendorcode;
	private String userid;
	private String username;
	private String usetype;
	private String useremail;
	private DOTaxDocumentAddDataDTO[] adddata;
	
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
	public String getUsetype() {
		return usetype;
	}
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public DOTaxDocumentAddDataDTO[] getAdddata() {
		return adddata;
	}
	public void setAdddata(DOTaxDocumentAddDataDTO[] adddata) {
		this.adddata = adddata;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
		
}
