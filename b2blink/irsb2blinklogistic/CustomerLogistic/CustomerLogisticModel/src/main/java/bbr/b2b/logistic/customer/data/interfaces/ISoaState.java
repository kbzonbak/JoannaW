package bbr.b2b.logistic.customer.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ISoaState extends IElement {
	public Date getWhen();
	public void setWhen(Date when);
}
