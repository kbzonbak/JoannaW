package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class DeleteInvoiceVendorRollOutInitParamDTO implements Serializable {

	private Long[] vendorids;

	public Long[] getVendorids() {
		return vendorids;
	}

	public void setVendorids(Long[] vendorids) {
		this.vendorids = vendorids;
	}
			
}
