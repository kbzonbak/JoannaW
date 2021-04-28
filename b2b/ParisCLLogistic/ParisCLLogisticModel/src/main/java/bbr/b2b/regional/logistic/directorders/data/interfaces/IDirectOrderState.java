package bbr.b2b.regional.logistic.directorders.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDirectOrderState extends IElement {

	public Date getWhen();
	public String getWho();
	public String getComment();
	public void setWhen(Date when);
	public void setWho(String who);
	public void setComment(String comment);
}
