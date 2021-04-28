package bbr.b2b.regional.logistic.taxdocuments.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.taxdocuments.data.interfaces.ITaxDocument;

public class TaxDocumentW extends ElementDTO implements ITaxDocument {

	private Long number;
	private Date when;
	private Date emitted;
	private Double amount;
	private String pdfurl;
	private String xmlurl;
	private String receptionnumber;
	private Date receptiondate;
	private Boolean validated;
	private Long vendorid;
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
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
	public String getPdfurl() {
		return pdfurl;
	}
	public void setPdfurl(String pdfurl) {
		this.pdfurl = pdfurl;
	}
	public String getXmlurl() {
		return xmlurl;
	}
	public void setXmlurl(String xmlurl) {
		this.xmlurl = xmlurl;
	}
	public String getReceptionnumber() {
		return receptionnumber;
	}
	public void setReceptionnumber(String receptionnumber) {
		this.receptionnumber = receptionnumber;
	}
	public Date getReceptiondate() {
		return receptiondate;
	}
	public void setReceptiondate(Date receptiondate) {
		this.receptiondate = receptiondate;
	}
	public Boolean getValidated() {
		return validated;
	}
	public void setValidated(Boolean validated) {
		this.validated = validated;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
}
