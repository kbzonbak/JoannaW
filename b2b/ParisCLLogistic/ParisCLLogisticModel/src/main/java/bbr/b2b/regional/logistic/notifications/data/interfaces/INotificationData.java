package bbr.b2b.regional.logistic.notifications.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface INotificationData extends IElement {

	public String getNumber();
	public Date getLoaddate();
	public void setNumber(String number);
	public void setLoaddate(Date loaddate);
}
