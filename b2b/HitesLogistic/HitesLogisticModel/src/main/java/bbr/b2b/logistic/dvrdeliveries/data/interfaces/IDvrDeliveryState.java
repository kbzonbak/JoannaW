package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDvrDeliveryState extends IElement {

	public LocalDateTime getWhen();
	public String getUser();
	public String getUsertype();
	public LocalDateTime getUserwhen();
	public String getComment();
	public void setWhen(LocalDateTime when);
	public void setUser(String user);
	public void setUsertype(String usertype);
	public void setUserwhen(LocalDateTime userwhen);
	public void setComment(String comment);
}
