package bbr.b2b.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VendorArrayResultDTO extends BaseResultDTO {

	private VendorDTO[] vendors;
	private Integer total;

	public VendorDTO[] getVendors() {
		return vendors;
	}

	public void setVendors(VendorDTO[] vendors) {
		this.vendors = vendors;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
