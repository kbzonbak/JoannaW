package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VendorHiredCourierResultsDTO extends BaseResultDTO {

	private VendorHiredCourierDTO[] vendorHiredCourier;

	public VendorHiredCourierDTO[] getVendorHiredCourier() {
		return vendorHiredCourier;
	}

	public void setVendorHiredCourier(VendorHiredCourierDTO[] vendorHiredCourier) {
		this.vendorHiredCourier = vendorHiredCourier;
	}
	
}
