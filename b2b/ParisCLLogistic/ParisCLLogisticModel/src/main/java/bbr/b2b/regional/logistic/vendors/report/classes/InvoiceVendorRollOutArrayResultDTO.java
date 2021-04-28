package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class InvoiceVendorRollOutArrayResultDTO extends BaseResultDTO {

	private InvoiceVendorRollOutDTO[] vendorrollouts;
	private int totalreg;

	public InvoiceVendorRollOutDTO[] getVendorrollouts() {
		return vendorrollouts;
	}

	public void setVendorrollouts(InvoiceVendorRollOutDTO[] vendorrollouts) {
		this.vendorrollouts = vendorrollouts;
	}

	public int getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(int totalreg) {
		this.totalreg = totalreg;
	}		
}
