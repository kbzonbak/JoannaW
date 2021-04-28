package bbr.b2b.regional.logistic.fillrate.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IFillRate extends IElement {

	public Double getTotalunits();
	public Double getReceivedunits();
	public Double getTotalneed();
	public Double getTotalreceived();
	public Integer getCountorders();
	public void setTotalunits(Double totalunits);
	public void setReceivedunits(Double receivedunits);
	public void setTotalneed(Double totalneed);
	public void setTotalreceived(Double totalreceived);
	public void setCountorders(Integer countorders);
}
