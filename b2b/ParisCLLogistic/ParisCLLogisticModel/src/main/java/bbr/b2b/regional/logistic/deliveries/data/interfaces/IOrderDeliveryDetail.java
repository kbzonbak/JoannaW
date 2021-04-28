package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IOrderDeliveryDetail extends IIdentifiable {

	public Double getPendingunits();
	public Double getAvailableunits();
	public Double getReceivedunits();
	public void setPendingunits(Double pendingunits);
	public void setAvailableunits(Double availableunits);
	public void setReceivedunits(Double receivedunits);
}
