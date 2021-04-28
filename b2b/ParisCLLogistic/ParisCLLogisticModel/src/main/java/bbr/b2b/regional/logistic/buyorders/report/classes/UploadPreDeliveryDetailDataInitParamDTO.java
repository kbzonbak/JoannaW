package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class UploadPreDeliveryDetailDataInitParamDTO implements Serializable {
  
	private String vendorcode;
	private String filename;
	private PreDeliveryDetailDataDTO[] details;
	
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
	public PreDeliveryDetailDataDTO[] getDetails() {
		return details;
	}
	public void setDetails(PreDeliveryDetailDataDTO[] details) {
		this.details = details;
	}	
}
