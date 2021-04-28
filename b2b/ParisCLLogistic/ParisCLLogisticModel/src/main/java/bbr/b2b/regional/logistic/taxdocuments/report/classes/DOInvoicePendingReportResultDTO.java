package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DOInvoicePendingReportResultDTO extends BaseResultDTO {

	private DOInvoicePendingDTO[] invoicepending;

	public DOInvoicePendingDTO[] getInvoicepending() {
		return invoicepending;
	}

	public void setInvoicepending(DOInvoicePendingDTO[] invoicepending) {
		this.invoicepending = invoicepending;
	}
	
}
