package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPallet extends IElement {

	public Integer getBoxcount();
	public void setBoxcount(Integer boxcount);
}
