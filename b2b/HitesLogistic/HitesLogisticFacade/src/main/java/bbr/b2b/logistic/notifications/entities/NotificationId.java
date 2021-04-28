package bbr.b2b.logistic.notifications.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.notifications.data.interfaces.INotificationPK;

public class NotificationId implements INotificationPK {

	private Long contactid;
	private Long notificationtypeid;
	private Long notificationtimeid;

	public NotificationId(){
	}
	public NotificationId(Long contactid, Long notificationtypeid, Long notificationtimeid){
		this.contactid = contactid;
		this.notificationtypeid = notificationtypeid;
		this.notificationtimeid = notificationtimeid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof NotificationId){
			NotificationId that = (NotificationId) o;
			return this.contactid.equals(that.contactid) && this.notificationtypeid.equals(that.notificationtypeid) && this.notificationtimeid.equals(that.notificationtimeid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return contactid.hashCode() + notificationtypeid.hashCode() + notificationtimeid.hashCode();
	}
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
