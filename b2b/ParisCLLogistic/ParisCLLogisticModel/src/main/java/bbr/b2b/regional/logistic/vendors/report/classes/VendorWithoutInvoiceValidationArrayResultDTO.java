package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VendorWithoutInvoiceValidationArrayResultDTO extends BaseResultDTO {

	private VendorWithoutInvoiceValidationDTO[] vendors;
	private VendorWithoutInvoiceValidationSummaryDTO summary;
		
	public VendorWithoutInvoiceValidationDTO[] getVendors() {
		return vendors;
	}
	public void setVendors(VendorWithoutInvoiceValidationDTO[] vendors) {
		this.vendors = vendors;
	}
	public VendorWithoutInvoiceValidationSummaryDTO getSummary() {
		return summary;
	}
	public void setSummary(VendorWithoutInvoiceValidationSummaryDTO summary) {
		this.summary = summary;
	}
}
