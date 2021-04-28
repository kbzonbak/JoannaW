package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVUpdateAuditDetailReportInitParamDTO implements Serializable{

	private String vendorcode;
	private String updatedate;
	private String updatetype;
	
	public String getUpdatetype() {
		return updatetype;
	}

	public void setUpdatetype(String updatetype) {
		this.updatetype = updatetype;
	}



	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

}
