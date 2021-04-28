package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class UpdateVeVLeadTimeInitParamDTO extends UserDataInitParamDTO implements Serializable {
	
	private String vendorcode;
	private VeVLeadTimeReportDataDTO[] data;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public VeVLeadTimeReportDataDTO[] getData() {
		return data;
	}
	public void setData(VeVLeadTimeReportDataDTO[] data) {
		this.data = data;
	}	
}
