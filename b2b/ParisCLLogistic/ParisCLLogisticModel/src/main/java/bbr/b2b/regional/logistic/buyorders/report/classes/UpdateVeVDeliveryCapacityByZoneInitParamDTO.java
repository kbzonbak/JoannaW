package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class UpdateVeVDeliveryCapacityByZoneInitParamDTO extends UserDataInitParamDTO implements Serializable {
	
	private String vendorcode;	
	private VeVDeliveryCapacitiesByZoneReportDataDTO[] data;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public VeVDeliveryCapacitiesByZoneReportDataDTO[] getData() {
		return data;
	}
	public void setData(VeVDeliveryCapacitiesByZoneReportDataDTO[] data) {
		this.data = data;
	}
	
}
