package bbr.b2b.logistic.notifications.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface INotificationPK extends IPrimaryKey {

	public Long getContactid();
	public Long getNotificationtypeid();
	public Long getNotificationtimeid();
	public void setContactid(Long contactid);
	public void setNotificationtypeid(Long notificationtypeid);
	public void setNotificationtimeid(Long notificationtimeid);
}
