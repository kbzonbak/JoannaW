package bbr.b2b.logistic.notifications.data.wrappers;

import bbr.b2b.logistic.notifications.data.interfaces.INotificationPK;
import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.notifications.data.interfaces.INotification;

public class NotificationW implements INotificationPK, INotification {

	private Long contactid;
	private Long notificationtypeid;
	private Long notificationtimeid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = contactid.longValue() - ((INotificationPK) arg0).getContactid().longValue();
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

	public Long getContactid(){ 
		return this.contactid;
	}
	public Long getNotificationtypeid(){ 
		return this.notificationtypeid;
	}
	public Long getNotificationtimeid(){ 
		return this.notificationtimeid;
	}
	public void setContactid(Long contactid){ 
		this.contactid = contactid;
	}
	public void setNotificationtypeid(Long notificationtypeid){ 
		this.notificationtypeid = notificationtypeid;
	}
	public void setNotificationtimeid(Long notificationtimeid){ 
		this.notificationtimeid = notificationtimeid;
	}
}
