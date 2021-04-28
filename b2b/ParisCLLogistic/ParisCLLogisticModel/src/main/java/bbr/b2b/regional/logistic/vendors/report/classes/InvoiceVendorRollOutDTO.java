package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InvoiceVendorRollOutDTO implements Serializable {

	private Long vendorid;
	private String vendorcode;
	private String vendorname;
	private Long invoicesincepropid;
	private LocalDateTime invoicesince;
	private Long invoicemaxpreviousdayspropid;
	private Integer invoicemaxpreviousdays;
	private LocalDateTime lastmodified;
	private String modifier;
	
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long getInvoicesincepropid() {
		return invoicesincepropid;
	}
	public void setInvoicesincepropid(Long invoicesincepropid) {
		this.invoicesincepropid = invoicesincepropid;
	}
	public LocalDateTime getInvoicesince() {
		return invoicesince;
	}
	public void setInvoicesince(LocalDateTime invoicesince) {
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
	public LocalDateTime getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(LocalDateTime lastmodified) {
		this.lastmodified = lastmodified;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
}
