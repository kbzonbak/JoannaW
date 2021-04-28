package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class VendorAddressInitParamDTO extends UserDataInitParamDTO implements Serializable {

	private VendorAddressDTO vendoraddress;

	public VendorAddressDTO getVendoraddress() {
		return vendoraddress;
	}

	public void setVendoraddress(VendorAddressDTO vendoraddress) {
		this.vendoraddress = vendoraddress;
	}
}
