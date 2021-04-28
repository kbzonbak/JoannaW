package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class UploadDODeliveryUpdatesInitParamDTO extends UserDataInitParamDTO {
	
	private String vendorcode;
	private String filename;
	private ExcelDODeliveryReportDataDTO[] data;
	
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
	public ExcelDODeliveryReportDataDTO[] getData() {
		return data;
	}
	public void setData(ExcelDODeliveryReportDataDTO[] data) {
		this.data = data;
	}	
}
