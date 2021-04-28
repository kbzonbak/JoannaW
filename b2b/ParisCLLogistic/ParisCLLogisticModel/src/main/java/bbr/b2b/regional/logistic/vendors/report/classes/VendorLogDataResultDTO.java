package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VendorLogDataResultDTO extends BaseResultDTO {

	private VendorLogDTO[] lastVendorSelected 	= null;
	private VendorLogDTO[] mostUsedVendors  	= null;
	private Integer totalvendors		= null;
	
	public VendorLogDTO[] getLastVendorSelected() {
		return lastVendorSelected;
	}
	public void setLastVendorSelected(VendorLogDTO[] lastVendorSelected) {
		this.lastVendorSelected = lastVendorSelected;
	}
	public VendorLogDTO[] getMostUsedVendors() {
		return mostUsedVendors;
	}
	public void setMostUsedVendors(VendorLogDTO[] mostUsedVendors) {
		this.mostUsedVendors = mostUsedVendors;
	}
	public Integer getTotalvendors() {
		return totalvendors;
	}
	public void setTotalvendors(Integer totalvendors) {
		this.totalvendors = totalvendors;
	}	
}
