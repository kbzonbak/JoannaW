package bbr.b2b.logistic.ddcorders.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDdcOrder extends IElement {

	public LocalDateTime getOriginaldeliverydate();
	public void setOriginaldeliverydate(LocalDateTime originaldeliverydate);
	public LocalDateTime getCurrentdeliverydate();
	public void setCurrentdeliverydate(LocalDateTime currentdeliverydate);
	public LocalDateTime getExpiration();
	public void setExpiration(LocalDateTime expiration);
	public Double getNeedunits();
	public void setNeedunits(Double needunits);
	public Double getTodeliveryunits();
	public void setTodeliveryunits(Double todeliveryunits);
	public Double getReceivedunits();
	public void setReceivedunits(Double receivedunits);
	public Double getPendingunits();
	public void setPendingunits(Double pendingunits);
	public Double getTotalneed();
	public void setTotalneed(Double totalneed);
	public Double getTotaltodelivery();
	public void setTotaltodelivery(Double totaltodelivery);
	public Double getTotalreceived();
	public void setTotalreceived(Double totalreceived);
	public Double getTotalpending();
	public void setTotalpending(Double totalpending);
	public String getPaymentdays();
	public void setPaymentdays(String paymentdays);
	public String getComment();
	public void setComment(String comment);
	public LocalDateTime getCurrentstatetypedate();
	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate);
	public String getCurrentstatetypewho();
	public void setCurrentstatetypewho(String currentstatetypewho);
	public String getCurrentstatetypecomment();
	public void setCurrentstatetypecomment(String currentstatetypecomment);
	public String getReferencenumber();
	public void setReferencenumber(String referencenumber);
	public Long getDispatchguide();
	public void setDispatchguide(Long dispatchguide);
	public Integer getReschedulingcounter() ;
	public void setReschedulingcounter(Integer reschedulingcounter);
}
