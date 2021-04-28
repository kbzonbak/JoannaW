package bbr.b2b.logistic.dvrorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDvrPreDeliveryDetail extends IIdentifiable {

	public Double getTotalunits();
	public Double getTodeliveryunits();
	public Double getReceivedunits();
	public Double getPendingunits();
	public Double getTotalneed();
	public Double getTotaltodelivery();
	public Double getTotalreceived();
	public Double getTotalpending();
	public void setTotalunits(Double totalunits);
	public void setTodeliveryunits(Double todeliveryunits);
	public void setReceivedunits(Double receivedunits);
	public void setPendingunits(Double pendingunits);
	public void setTotalneed(Double totalneed);
	public void setTotaltodelivery(Double totaltodelivery);
	public void setTotalreceived(Double totalreceived);
	public void setTotalpending(Double totalpending);
}
