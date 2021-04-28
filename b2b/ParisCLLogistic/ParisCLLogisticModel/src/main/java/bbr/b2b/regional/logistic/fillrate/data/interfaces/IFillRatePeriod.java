package bbr.b2b.regional.logistic.fillrate.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IFillRatePeriod extends IElement {

	public String getName();
	public Date getSince();
	public Date getUntil();
	public void setName(String name);
	public void setSince(Date since);
	public void setUntil(Date until);
}
