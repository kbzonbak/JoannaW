package bbr.b2b.logistic.customer.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrder extends IElement {

	public Date getCreated();
	public Long getNumber();
	public String getTicket();
	public String getReceiptnumber();
	public String getRequest();
	public String getStatus();
	public String getNumref1();
	public String getNumref2();
	public String getNumref3();
	public Double getTotal();
	public Date getIssue_date();
	public Date getEffectiv_date();
	public Date getExpiration_date();
	public Date getCommitment_date();
	public String getPayment_condition();
	public String getObservation();
	public String getResponsible();
	public String getResponsible_email();
	public Boolean getValid();
	public Boolean getComplete();
	public void setCreated(Date created);
	public void setNumber(Long number);
	public void setTicket(String ticket);
	public void setReceiptnumber(String receiptnumber);
	public void setRequest(String request);
	public void setStatus(String status);
	public void setNumref1(String numref1);
	public void setNumref2(String numref2);
	public void setNumref3(String numref3);
	public void setTotal(Double total);
	public void setIssue_date(Date issue_date);
	public void setEffectiv_date(Date effectiv_date);
	public void setExpiration_date(Date expiration_date);
	public void setCommitment_date(Date commitment_date);
	public void setPayment_condition(String payment_condition);
	public void setObservation(String observation);
	public void setResponsible(String responsible);
	public void setResponsible_email(String responsible_email);
	public void setValid(Boolean valid);
	public void setComplete(Boolean complete);
	public String getCurrency();
	public void setCurrency(String currency);
}
