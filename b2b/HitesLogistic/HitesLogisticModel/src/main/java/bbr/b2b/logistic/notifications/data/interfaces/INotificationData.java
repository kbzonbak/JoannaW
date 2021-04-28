package bbr.b2b.logistic.notifications.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface INotificationData extends IElement {

	public String getNumber();
	public LocalDateTime getLoaddate();
	public void setNumber(String number);
	public void setLoaddate(LocalDateTime loaddate);
}
