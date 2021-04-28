package bbr.b2b.regional.logistic.items.report.classes;

import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;

public class VendorItemDataW extends ItemW{

	private boolean directdelivery;
	private String vendoritemcode;
	
	public boolean isDirectdelivery() {
		return directdelivery;
	}
	public void setDirectdelivery(boolean directdelivery) {
		this.directdelivery = directdelivery;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}	
}
