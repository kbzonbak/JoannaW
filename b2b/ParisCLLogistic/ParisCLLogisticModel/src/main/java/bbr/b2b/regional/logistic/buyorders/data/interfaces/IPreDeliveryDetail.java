package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IPreDeliveryDetail extends IIdentifiable {

	public Integer getSequence();
	public Double getUnits();
	public Double getPendingunits();
	public Double getReceivedunits();
	public Double getTodeliveryunits();
	public Double getOutreceivedunits();
	public Double getTotalneed();
	public Double getTotalpending();
	public Double getTotalreceived();
	public Double getTotaltodelivery();
	public void setSequence(Integer sequence);
	public void setUnits(Double units);
	public void setPendingunits(Double pendingunits);
	public void setReceivedunits(Double receivedunits);
	public void setTodeliveryunits(Double todeliveryunits);
	public void setOutreceivedunits(Double outreceivedunits);
	public void setTotalneed(Double totalneed);
	public void setTotalpending(Double totalpending);
	public void setTotalreceived(Double totalreceived);
	public void setTotaltodelivery(Double totaltodelivery);
}
