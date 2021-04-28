package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IItemAtc extends IIdentifiable {
	public Long getCurve();
	public void setCurve(Long curve);
}
