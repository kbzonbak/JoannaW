package bbr.b2b.logistic.dvrorders.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDvrOrder extends IElement {

	public LocalDateTime getDeliverydate();
	public LocalDateTime getExpiration();
	public String getPaymenttype();
	public String getComment();
	public Boolean getMultidelivery();
	public String getWarehousecode();
	public Double getTotalunits();
	public Double getTodeliveryunits();
	public Double getReceivedunits();
	public Double getPendingunits();
	public Double getTotalneed();
	public Double getTotaltodelivery();
	public Double getTotalreceived();
	public Double getTotalpending();
	public LocalDateTime getCurrentstatetypedate();
	public String getReferencenumber();
	public String getPaymentdays();
	public LocalDateTime getClientdeliverydate();
	public Double getNetamount();
	public Double getTaxamount();
	public Double getTotalamountoc();
	public LocalDateTime getReschedulingdate();
	public Integer getReschedulingcounter();
	public void setDeliverydate(LocalDateTime deliverydate);
	public void setExpiration(LocalDateTime expiration);
	public void setPaymenttype(String paymenttype);
	public void setComment(String comment);
	public void setMultidelivery(Boolean multidelivery);
	public void setWarehousecode(String warehousecode);
	public void setTotalunits(Double totalunits);
	public void setTodeliveryunits(Double todeliveryunits);
	public void setReceivedunits(Double receivedunits);
	public void setPendingunits(Double pendingunits);
	public void setTotalneed(Double totalneed);
	public void setTotaltodelivery(Double totaltodelivery);
	public void setTotalreceived(Double totalreceived);
	public void setTotalpending(Double totalpending);
	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate);
	public void setReferencenumber(String referencenumber);
	public void setPaymentdays(String paymentdays);
	public void setClientdeliverydate(LocalDateTime clientdeliverydate);
	public void setNetamount(Double netamount);
	public void setTaxamount(Double taxamount);
	public void setTotalamountoc(Double totalamountoc);
	public void setReschedulingdate(LocalDateTime reschedulingdate);
	public void setReschedulingcounter(Integer reschedulingcounter);
	
}
