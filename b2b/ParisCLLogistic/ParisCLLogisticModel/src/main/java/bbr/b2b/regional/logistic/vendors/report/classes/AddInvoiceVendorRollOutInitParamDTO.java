package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class AddInvoiceVendorRollOutInitParamDTO implements Serializable {

	private String vendorcode;
	private String invoicesince;
	private Integer invoicemaxpreviousdays;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getInvoicesince() {
		return invoicesince;
	}
	public void setInvoicesince(String invoicesince) {
		this.invoicesince = invoicesince;
	}
	public Integer getInvoicemaxpreviousdays() {
		return invoicemaxpreviousdays;
	}
	public void setInvoicemaxpreviousdays(Integer invoicemaxpreviousdays) {
		this.invoicemaxpreviousdays = invoicemaxpreviousdays;
	}
				
}
