package bbr.b2b.regional.logistic.kpi.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevFine extends IElement{

	public Double getTotalfinefactor();
	public void setTotalfinefactor(Double totalfinefactor);
	public Double getCompliance();
	public void setCompliance(Double compliance);
	public Double getFinalfine();
	public void setFinalfine(Double finalfine);
	
}