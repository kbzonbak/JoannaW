package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class UpdateVeVAvailableStockInitParamDTO extends UserDataInitParamDTO implements Serializable {
	
	private String vendorcode;
	private VeVAvailableStockReportDataDTO[] data;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public VeVAvailableStockReportDataDTO[] getData() {
		return data;
	}
	public void setData(VeVAvailableStockReportDataDTO[] data) {
		this.data = data;
	}	
}
