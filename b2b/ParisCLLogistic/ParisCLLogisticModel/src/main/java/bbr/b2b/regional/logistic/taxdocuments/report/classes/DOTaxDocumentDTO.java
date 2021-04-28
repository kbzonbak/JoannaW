package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DOTaxDocumentDTO implements Serializable {

	private Long id;
	private Long number;
	private LocalDateTime emitted;
	private Double amount;
	private LocalDateTime currentstatetypedate;
	private Long vendorid;
	private Long directorderid;
	private Long currentstatetypeid;
	private Long dotaxdocumentticketid;
	private String pdfurl;

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

	public LocalDateTime getEmitted() {
		return emitted;
	}

	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getCurrentstatetypedate() {
		return currentstatetypedate;
	}

	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate) {
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