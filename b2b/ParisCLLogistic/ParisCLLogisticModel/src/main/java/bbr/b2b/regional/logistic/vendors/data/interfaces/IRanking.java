package bbr.b2b.regional.logistic.vendors.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IRanking extends IElement {

	public Date getWhen();
	public Date getSince();
	public Date getUntil();
	public void setWhen(Date when);
	public void setSince(Date since);
	public void setUntil(Date until);
}
