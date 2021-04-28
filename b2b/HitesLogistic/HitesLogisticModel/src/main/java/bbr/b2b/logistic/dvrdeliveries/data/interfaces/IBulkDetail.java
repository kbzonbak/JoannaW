package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IBulkDetail extends IIdentifiable {

	public Double getUnits();
	public void setUnits(Double units);
	public String getBatchnumber();
	public void setBatchnumber(String batchnumber);

}
