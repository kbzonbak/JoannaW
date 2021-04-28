package bbr.b2b.regional.logistic.kpi.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevComplianceFactor extends IElement{

	public Double getMin();
	public void setMin(Double min);
	public Double getMax();
	public void setMax(Double max);
	public Double getFactor();
	public void setFactor(Double factor);
	
}