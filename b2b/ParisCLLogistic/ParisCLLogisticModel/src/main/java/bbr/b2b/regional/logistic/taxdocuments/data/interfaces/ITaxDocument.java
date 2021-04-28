package bbr.b2b.regional.logistic.taxdocuments.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ITaxDocument extends IElement {

	public Long getNumber();
	public void setNumber(Long number);
	public Date getWhen();
	public void setWhen(Date when);
	public Date getEmitted();
	public void setEmitted(Date emitted);
	public Double getAmount();
	public void setAmount(Double amount);
	public String getPdfurl();
	public void setPdfurl(String pdfurl);
	public String getXmlurl();
	public void setXmlurl(String xmlurl);
	public String getReceptionnumber();
	public void setReceptionnumber(String receptionnumber);
	public Date getReceptiondate();
	public void setReceptiondate(Date receptiondate);
	public Boolean getValidated();
	public void setValidated(Boolean validated);
	
}
