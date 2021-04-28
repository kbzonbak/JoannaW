package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class UpdateInvoiceVendorRollOutInitParamDTO implements Serializable {

	private String vendorcode;
	private Long invoicesincepropid;
	private String invoicesince;
	private Long invoicemaxpreviousdayspropid;
	private Integer invoicemaxpreviousdays;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getInvoicesincepropid() {
		return invoicesincepropid;
	}
	public void setInvoicesincepropid(Long invoicesincepropid) {
		this.invoicesincepropid = invoicesincepropid;
	}
	public String getInvoicesince() {
		return invoicesince;
	}
	public void setInvoicesince(String invoicesince) {
		this.invoicesince = invoicesince;
	}
	public Long getInvoicemaxpreviousdayspropid() {
		return invoicemaxpreviousdayspropid;
	}
	public void setInvoicemaxpreviousdayspropid(Long invoicemaxpreviousdayspropid) {
		this.invoicemaxpreviousdayspropid = invoicemaxpreviousdayspropid;
	}
	public Integer getInvoicemaxpreviousdays() {
		return invoicemaxpreviousdays;
	}
	public void setInvoicemaxpreviousdays(Integer invoicemaxpreviousdays) {
		this.invoicemaxpreviousdays = invoicemaxpreviousdays;
	}
	
}
