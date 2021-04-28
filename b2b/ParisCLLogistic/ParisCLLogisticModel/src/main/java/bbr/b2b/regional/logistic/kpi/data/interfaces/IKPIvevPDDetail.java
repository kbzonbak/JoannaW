package bbr.b2b.regional.logistic.kpi.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevPDDetail extends IElement{

	public Long getOrdernumber();
	public void setOrdernumber(Long ordernumber);
	public String getOrderrequestnumber();
	public void setOrderrequestnumber(String orderrequestnumber);
	public String getCurrentorderstate();
	public void setCurrentorderstate(String currentorderstate);
	public String getCurrentdeliverystate();
	public void setCurrentdeliverystate(String currentdeliverystate);
	public Date getDeliverycurrentdate();
	public void setDeliverycurrentdate(Date deliverycurrentdate);
	public Date getCourierpendingdate();
	public void setCourierpendingdate(Date courierpendingdate);
	public Date getFcm();
	public void setFcm(Date fcm);
	public Date getFrep();
	public void setFrep(Date frep);
	public Date getSendingdate();
	public void setSendingdate(Date sendingdate);
	public Date getClientrescheduledate();
	public void setClientrescheduledate(Date clientrescheduledate);
	public Date getVendorrescheduledate();
	public void setVendorrescheduledate(Date vendorrescheduledate);
	public Integer getDeliverydelayeddays();
	public void setDeliverydelayeddays(Integer deliverydelayeddays);
	public Integer getCourierdeliverydelayeddays();
	public void setCourierdeliverydelayeddays(Integer courierdeliverydelayeddays);
	public Integer getDelayeddays();
	public void setDelayeddays(Integer delayeddays);
	public String getRescheduleresponsibility();
	public void setRescheduleresponsibility(String rescheduleresponsibility);
	public String getIteminternalcode();
	public void setIteminternalcode(String iteminternalcode);
	public String getVendoritemcode();
	public void setVendoritemcode(String vendoritemcode);
	public String getItemname();
	public void setItemname(String itemname);
	public Double getItemfinalcost();
	public void setItemfinalcost(Double itemfinalcost);
	public Double getDetailunits();
	public void setDetailunits(Double detailunits);
	public Double getDetailtodeliveryunits();
	public void setDetailtodeliveryunits(Double detailtodeliveryunits);
	public Double getDetailreceivedunits();
	public void setDetailreceivedunits(Double detailreceivedunits);
	public Double getDetailpendingunits();
	public void setDetailpendingunits(Double detailpendingunits);
	public Integer getFinefactordays();
	public void setFinefactordays(Integer finefactordays);
	public Double getFinefactor();
	public void setFinefactor(Double finefactor);
}
