package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class UpdateVeVDeliveryTimesInitParamDTO extends UserDataInitParamDTO implements Serializable {
	
	private String vendorcode;	
	private VeVDeliveryTimesReportDataDTO[] data;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public VeVDeliveryTimesReportDataDTO[] getData() {
		return data;
	}
	public void setData(VeVDeliveryTimesReportDataDTO[] data) {
		this.data = data;
	}
}
