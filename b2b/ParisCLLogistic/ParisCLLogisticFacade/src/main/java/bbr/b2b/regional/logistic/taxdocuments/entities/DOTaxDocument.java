package bbr.b2b.regional.logistic.taxdocuments.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.taxdocuments.data.interfaces.IDOTaxDocument;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class DOTaxDocument implements IDOTaxDocument {

	private Long id;
	private Long number;
	private Date emitted;
	private Double amount;
	private Date currentstatetypedate;
	private String pdfurl;
	private Vendor vendor;
	private DirectOrder directorder;
	private DOTaxDocumentStateType currentstatetype;
	private DOTaxDocumentTicket doTaxDocumentTicket;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Date getEmitted() {
		return emitted;
	}
	public void setEmitted(Date emitted) {
		this.emitted = emitted;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getCurrentstatetypedate() {
		return currentstatetypedate;
	}
	public void setCurrentstatetypedate(Date currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}
	public String getPdfurl() {
		return pdfurl;
	}
	public void setPdfurl(String pdfurl) {
		this.pdfurl = pdfurl;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public DirectOrder getDirectorder() {
		return directorder;
	}
	public void setDirectorder(DirectOrder directorder) {
		this.directorder = directorder;
	}
	public DOTaxDocumentStateType getCurrentstatetype() {
		return currentstatetype;
	}
	public void setCurrentstatetype(DOTaxDocumentStateType currentstatetype) {
		this.currentstatetype = currentstatetype;
	}
	public DOTaxDocumentTicket getDoTaxDocumentTicket() {
		return doTaxDocumentTicket;
	}
	public void setDoTaxDocumentTicket(DOTaxDocumentTicket doTaxDocumentTicket) {
		this.doTaxDocumentTicket = doTaxDocumentTicket;
	}
	
}