package bbr.b2b.regional.logistic.kpi.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevCDDetail extends IElement{

	public Long getOrdernumber();
	public void setOrdernumber(Long ordernumber);
	public String getOrderrequestnumber();
	public void setOrderrequestnumber(String orderrequestnumber);
	public String getCurrentorderstate();
	public void setCurrentorderstate(String currentorderstate);
	public Date getCurrentorderstatedate();
	public void setCurrentorderstatedate(Date currentorderstatedate);
	public Date getFpi();
	public void setFpi(Date fpi);
	public Date getSendingdate();
	public void setSendingdate(Date sendingdate);
	public Integer getDeliverydelayeddays();
	public void setDeliverydelayeddays(Integer deliverydelayeddays);
	public Integer getDelayeddays();
	public void setDelayeddays(Integer delayeddays);
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
	public String getFinallocationcode();
	public void setFinallocationcode(String finallocationcode);
	public String getFinallocationname();
	public void setFinallocationname(String finallocationname);
	public Integer getFinefactordays();
	public void setFinefactordays(Integer finefactordays);
	public Double getFinefactor();
	public void setFinefactor(Double finefactor);
}
