package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IOutReceptionDetail extends IIdentifiable {

	public Double getReceivedunits();
	public void setReceivedunits(Double receivedunits);
}
