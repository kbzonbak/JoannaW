package bbr.b2b.regional.logistic.datings.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IReserve extends IElement {

	public Date getWhen();
	public void setWhen(Date when);
}
