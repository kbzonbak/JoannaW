package bbr.b2b.logistic.ddcorders.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDdcOrderState extends IElement {

	public LocalDateTime getWhen();
	public void setWhen(LocalDateTime when);
	public String getWho();
	public void setWho(String who);
	public String getComment();
	public void setComment(String comment);
}
