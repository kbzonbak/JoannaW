package bbr.b2b.regional.logistic.taxdocuments.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.taxdocuments.data.interfaces.IDOTaxDocument;

public class DOTaxDocumentW extends ElementDTO implements IDOTaxDocument {

	private Long number;
	private Date emitted;
	private Double amount;
	private Date currentstatetypedate;
	private Long vendorid;
	private Long directorderid;
	private Long currentstatetypeid;
	private Long dotaxdocumentticketid;
	private String pdfurl;
	
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
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getDirectorderid() {
		return directorderid;
	}
	public void setDirectorderid(Long directorderid) {
		this.directorderid = directorderid;
	}
	public Long getCurrentstatetypeid() {
		return currentstatetypeid;
	}
	public void setCurrentstatetypeid(Long currentstatetypeid) {
		this.currentstatetypeid = currentstatetypeid;
	}
	public Long getDotaxdocumentticketid() {
		return dotaxdocumentticketid;
	}
	public void setDotaxdocumentticketid(Long dotaxdocumentticketid) {
		this.dotaxdocumentticketid = dotaxdocumentticketid;
	}
	public String getPdfurl() {
		return pdfurl;
	}
	public void setPdfurl(String pdfurl) {
		this.pdfurl = pdfurl;
	}
			
}