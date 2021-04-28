package bbr.b2b.logistic.ddcdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDdcDeliveryDetail extends IIdentifiable {

	public Double getPendingunits();
	public void setPendingunits(Double pendingunits);
	public Double getAvailableunits();
	public void setAvailableunits(Double availableunits);
	public Double getReceivedunits();
	public void setReceivedunits(Double receivedunits);
}
