package bbr.b2b.logistic.notifications.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface INotificationTime extends IElement {

	public String getHourname();
	public LocalDateTime getHourdate();
	public Boolean getVisible();
	public void setHourname(String hourname);
	public void setHourdate(LocalDateTime hourdate);
	public void setVisible(Boolean visible);
}
