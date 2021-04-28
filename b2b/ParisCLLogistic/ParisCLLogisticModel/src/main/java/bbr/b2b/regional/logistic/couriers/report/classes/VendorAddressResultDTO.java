package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VendorAddressResultDTO extends BaseResultDTO {
	
	private VendorAddressDTO vendorAdress;

	public VendorAddressDTO getVendorAdress() {
		return vendorAdress;
	}
	public void setVendorAdress(VendorAddressDTO vendorAdress) {
		this.vendorAdress = vendorAdress;
	}

}
