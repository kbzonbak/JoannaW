package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrderState extends IElement {

	public Date getWhen();
	public void setWhen(Date when);
}
