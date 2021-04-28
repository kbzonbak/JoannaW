package bbr.b2b.regional.logistic.fillrate.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IFillRateDetail extends IIdentifiable {

	public Double getTotalunits();
	public Double getReceivedunits();
	public void setTotalunits(Double totalunits);
	public void setReceivedunits(Double receivedunits);
}
