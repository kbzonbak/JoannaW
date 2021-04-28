package bbr.b2b.logistic.ddcdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDdcDeliveryState extends IElement {

	public LocalDateTime getWhen();
	public void setWhen(LocalDateTime when);
	public String getUsername();
	public void setUsername(String username);
	public String getUsertype();
	public void setUsertype(String usertype);
	public LocalDateTime getStatedate();
	public void setStatedate(LocalDateTime statedate);
	public String getComment();
	public void setComment(String comment);
}
