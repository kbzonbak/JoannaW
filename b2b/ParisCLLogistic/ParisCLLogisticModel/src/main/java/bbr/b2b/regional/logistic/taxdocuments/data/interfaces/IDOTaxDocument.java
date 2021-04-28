package bbr.b2b.regional.logistic.taxdocuments.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDOTaxDocument extends IElement {

	public Long getNumber();
	public void setNumber(Long number);
	public Date getEmitted();
	public void setEmitted(Date emitted);
	public Double getAmount();
	public void setAmount(Double amount);
	public Date getCurrentstatetypedate();
	public void setCurrentstatetypedate(Date currentstatetypedate);
	public String getPdfurl();
	public void setPdfurl(String pdfurl);
	
}