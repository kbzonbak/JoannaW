package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;


public class UploadVeVLeadTimeInitParamDTO extends UserDataInitParamDTO implements Serializable{
	
	private String vendorcode;
	private String filename;
	private VeVLeadTimeDataDTO[] leadimelist;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public VeVLeadTimeDataDTO[] getLeadimelist() {
		return leadimelist;
	}
	public void setLeadimelist(VeVLeadTimeDataDTO[] leadimelist) {
		this.leadimelist = leadimelist;
	}		
}
