package bbr.b2b.regional.logistic.notifications.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface INotificationTime extends IElement {

	public String getHourname();

	public void setHourname(String hourname);

	public Date getHourdate();

	public void setHourdate(Date hourdate);
	
}
