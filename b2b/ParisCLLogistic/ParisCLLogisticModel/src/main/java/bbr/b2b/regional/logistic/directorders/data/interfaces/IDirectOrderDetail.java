package bbr.b2b.regional.logistic.directorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDirectOrderDetail extends IIdentifiable {

	public Integer getVisualorder();
	public Double getUnits();
	public Double getListprice();
	public Double getListcost();
	public Double getFinalprice();
	public Double getFinalcost();
	public Double getPendingunits();
	public Double getReceivedunits();
	public Double getTodeliveryunits();
	public Double getTotalpending();
	public Double getTotalreceived();
	public Double getTotaltodelivery();
	public Double getTotalneed();
	public void setVisualorder(Integer visualorder);
	public void setUnits(Double units);
	public void setListprice(Double listprice);
	public void setListcost(Double listcost);
	public void setFinalprice(Double finalprice);
	public void setFinalcost(Double finalcost);	
	public void setPendingunits(Double pendingunits);
	public void setReceivedunits(Double receivedunits);
	public void setTodeliveryunits(Double todeliveryunits);
	public void setTotalneed(Double totalneed);
	public void setTotalpending(Double totalpending);
	public void setTotalreceived(Double totalreceived);
	public void setTotaltodelivery(Double totaltodelivery);
}
