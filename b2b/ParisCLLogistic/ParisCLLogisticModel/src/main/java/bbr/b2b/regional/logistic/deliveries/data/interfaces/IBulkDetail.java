package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IBulkDetail extends IIdentifiable {

	public Double getUnits();
	public String getRefdocument();
	public void setUnits(Double units);
	public void setRefdocument(String refdocument);
}
