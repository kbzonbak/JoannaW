package bbr.b2b.regional.logistic.notifications.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface INotificationPK extends IPrimaryKey {

	public Long getVendorid();
	public Long getNotificationtypeid();
	public Long getNotificationtimeid();
	public void setVendorid(Long vendorid);
	public void setNotificationtypeid(Long notificationtypeid);
	public void setNotificationtimeid(Long notificationtimeid);
}
