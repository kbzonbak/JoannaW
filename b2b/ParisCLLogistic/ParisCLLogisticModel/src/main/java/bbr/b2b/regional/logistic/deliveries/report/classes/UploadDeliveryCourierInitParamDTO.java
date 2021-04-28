package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.util.List;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class UploadDeliveryCourierInitParamDTO extends UserDataInitParamDTO {

	private String filename;
	private String vendorcode;
	private List<Long> dodeliverynumbers;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public List<Long> getDodeliverynumbers() {
		return dodeliverynumbers;
	}
	public void setDodeliverynumbers(List<Long> dodeliverynumbers) {
		this.dodeliverynumbers = dodeliverynumbers;
	}
}
