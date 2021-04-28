package bbr.b2b.regional.logistic.directorders.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDirectOrder extends IElement {

	public Long getNumber();
	public String getRequestnumber();
	public Boolean getActive();
	public Date getEmitted();
	public Date getValid();
	public Date getExpiration();
	public Date getCurrentdeliverydate();
	public Date getOriginaldeliverydate();
	public Date getCurrentstatetypedate();
	public String getCurrentstatetypecomment();
	public String getCurrentstatetypewho();
	public Date getPaymentdate();
	public String getPaymenttype();
	public String getSeason();
	public String getClientaddress();
	public String getClientcity();
	public String getClientcommune();
	public String getClientdeptnumber();
	public String getClienthousenumber();
	public String getClientphone();
	public String getClientregion();
	public String getClientroadnumber();
	public Double getNeedunits();
	public Double getPendingunits();
	public Double getReceivedunits();
	public Double getTodeliveryunits();
	public Double getTotalpending();
	public Double getTotalreceived();
	public Double getTotaltodelivery();
	public Double getTotalneed();
	public String getComment();
	public String getDistributionordernumber();
	public Date getCurrentsoastatetypedate();
	public Boolean getCourierreceived();
	public void setNumber(Long number);
	public void setRequestnumber(String requestnumber);
	public void setActive(Boolean active);
	public void setEmitted(Date emitted);
	public void setValid(Date valid);
	public void setExpiration(Date expiration);
	public void setCurrentdeliverydate(Date currentdeliverydate);
	public void setOriginaldeliverydate(Date originaldeliverydate);
	public void setCurrentstatetypedate(Date currentstatetypedate);
	public void setCurrentstatetypecomment(String currentstatetypecomment);
	public void setCurrentstatetypewho(String currentstatetypewho);
	public void setPaymentdate(Date paymentdate);
	public void setPaymenttype(String paymenttype);
	public void setSeason(String season);
	public void setClientaddress(String clientaddress);
	public void setClientcity(String clientcity);
	public void setClientcommune(String clientcommune);
	public void setClientdeptnumber(String clientdeptnumber);
	public void setClienthousenumber(String clienthousenumber);
	public void setClientphone(String clientphone);
	public void setClientregion(String clientregion);
	public void setClientroadnumber(String clientroadnumber);
	public void setNeedunits(Double needunits);
	public void setPendingunits(Double pendingunits);
	public void setReceivedunits(Double receivedunits);
	public void setTodeliveryunits(Double todeliveryunits);
	public void setTotalpending(Double totalpending);
	public void setTotalreceived(Double totalreceived);
	public void setTotaltodelivery(Double totaltodelivery);
	public void setTotalneed(Double totalneed);
	public void setComment(String comment);	
	public void setDistributionordernumber(String distributionordernumber);	
	public void setCurrentsoastatetypedate(Date currentsoastatetypedate);	
	public void setCourierreceived(Boolean courierreceived);

}
