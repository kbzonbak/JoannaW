package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;


public class UploadVeVDeliveryCapacitiesByZoneInitParamDTO extends UserDataInitParamDTO implements Serializable{
	
	private String vendorcode;
	private String filename;
	private VeVDeliveryCapacitiesByZoneDataDTO[] capacitylist;
	

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public VeVDeliveryCapacitiesByZoneDataDTO[] getCapacitylist() {
		return capacitylist;
	}
	public void setCapacitylist(VeVDeliveryCapacitiesByZoneDataDTO[] capacitylist) {
		this.capacitylist = capacitylist;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}		
	
	
	
}
