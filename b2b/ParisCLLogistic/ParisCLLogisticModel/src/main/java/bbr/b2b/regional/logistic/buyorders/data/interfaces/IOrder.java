package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrder extends IElement {

	public Long getNumber();
	public String getRequestnumber();
	public Boolean getActive();
	public Date getEmitted();
	public Date getValid();
	public Date getExpiration();
	public Date getOriginaldeliverydate();
	public Date getCurrentstatetypedate();
	public String getComment();
	public Double getNeedunits();
	public Double getPendingunits();
	public Double getReceivedunits();
	public Double getTodeliveryunits();
	public Double getOutreceivedunits();
	public Double getTotalneed();
	public Double getTotalpending();
	public Double getTotalreceived();
	public Double getTotaltodelivery();
	public Boolean getVevcd();	
	public void setNumber(Long number);
	public void setRequestnumber(String requestnumber);
	public void setActive(Boolean active);
	public void setEmitted(Date emitted);
	public void setValid(Date valid);
	public void setOriginaldeliverydate(Date originaldeliverydate);
	public void setExpiration(Date expiration);
	public void setCurrentstatetypedate(Date currentstatetypedate);
	public void setComment(String comment);
	public void setNeedunits(Double needunits);
	public void setPendingunits(Double pendingunits);
	public void setReceivedunits(Double receivedunits);
	public void setTodeliveryunits(Double todeliveryunits);
	public void setOutreceivedunits(Double outreceivedunits);
	public void setTotalneed(Double totalneed);
	public void setTotalpending(Double totalpending);
	public void setTotalreceived(Double totalreceived);
	public void setTotaltodelivery(Double totaltodelivery);
	public void setVevcd(Boolean vevcd);	
	public Date getCurrentsoastatetypedate();
	public void setCurrentsoastatetypedate(Date currentsoastatetypedate);
	public String getDistributionordernumber();
	public void setDistributionordernumber(String distributionordernumber);	
	public String getVendorcodeimp();
	public void setVendorcodeimp(String vendorcodeimp);
}
