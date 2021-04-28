package bbr.b2b.logistic.dvrorders.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDvrOrderState extends IElement {

	public LocalDateTime getWhen();
	public String getWho();
	public void setWhen(LocalDateTime when);
	public void setWho(String who);
}
