package bbr.b2b.regional.logistic.evaluations.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IErrorFactor extends IIdentifiable {

	public Boolean getAffects();
	public void setAffects(Boolean affects);
}
