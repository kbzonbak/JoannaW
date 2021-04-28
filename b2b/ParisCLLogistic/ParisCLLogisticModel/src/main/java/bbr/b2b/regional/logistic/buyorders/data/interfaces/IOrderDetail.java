package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IOrderDetail extends IIdentifiable {

	public Integer getVisualorder();
	public Double getListprice();
	public Double getFinalprice();
	public Double getListcost();
	public Double getFinalcost();
	public Double getUnits();
	public Double getPendingunits();
	public Double getReceivedunits();
	public Double getTodeliveryunits();
	public Double getOutreceivedunits();
	public Double getTotalneed();
	public Double getTotalpending();
	public Double getTotalreceived();
	public Double getTotaltodelivery();
	public void setVisualorder(Integer visualorder);
	public void setListprice(Double listprice);
	public void setFinalprice(Double finalprice);
	public void setListcost(Double listcost);
	public void setFinalcost(Double finalcost);
	public void setUnits(Double units);
	public void setPendingunits(Double pendingunits);
	public void setReceivedunits(Double receivedunits);
	public void setTodeliveryunits(Double todeliveryunits);
	public void setOutreceivedunits(Double outreceivedunits);
	public void setTotalneed(Double totalneed);
	public void setTotalpending(Double totalpending);
	public void setTotalreceived(Double totalreceived);
	public void setTotaltodelivery(Double totaltodelivery);
	public Boolean getHasatc();
	public void setHasatc(Boolean hasatc);
}
