package bbr.b2b.regional.logistic.notifications.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.notifications.data.interfaces.INotification;
import bbr.b2b.regional.logistic.notifications.data.interfaces.INotificationPK;

public class NotificationW implements INotification, INotificationPK {

	private Long vendorid;
	private Long notificationtypeid;
	private Long notificationtimeid;

	public int compareTo(IPrimaryKey arg0) {
		long result = 0;
		result = vendorid.longValue() - ((INotificationPK) arg0).getVendorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = notificationtypeid.longValue() - ((INotificationPK) arg0).getNotificationtypeid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = notificationtimeid.longValue() - ((INotificationPK) arg0).getNotificationtimeid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getNotificationtypeid() {
		return this.notificationtypeid;
	}

	public Long getNotificationtimeid() {
		return this.notificationtimeid;
	}

	public Long getVendorid() {
		return vendorid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public void setNotificationtypeid(Long notificationtypeid) {
		this.notificationtypeid = notificationtypeid;
	}

	public void setNotificationtimeid(Long notificationtimeid) {
		this.notificationtimeid = notificationtimeid;
	}
}
