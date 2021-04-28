package bbr.b2b.regional.logistic.notifications.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.notifications.data.interfaces.INotificationPK;

public class NotificationId implements INotificationPK {

	private Long vendorid;
	private Long notificationtypeid;
	private Long notificationtimeid;

	public NotificationId(){
	}
	public NotificationId(Long vendorid, Long notificationtypeid, Long notificationtimeid){
		this.vendorid = vendorid;
		this.notificationtypeid = notificationtypeid;
		this.notificationtimeid = notificationtimeid;
	}

	public int compareTo(IPrimaryKey arg0){
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
	public boolean equals(Object o){
		if (o != null && o instanceof NotificationId){
			NotificationId that = (NotificationId) o;
			return this.vendorid.equals(that.vendorid) && this.notificationtypeid.equals(that.notificationtypeid) && this.notificationtimeid.equals(that.notificationtimeid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return vendorid.hashCode() + notificationtypeid.hashCode() + notificationtimeid.hashCode();
	}

	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getNotificationtypeid(){ 
		return this.notificationtypeid;
	}
	public Long getNotificationtimeid(){ 
		return this.notificationtimeid;
	}
	public void setNotificationtypeid(Long notificationtypeid){ 
		this.notificationtypeid = notificationtypeid;
	}
	public void setNotificationtimeid(Long notificationtimeid){ 
		this.notificationtimeid = notificationtimeid;
	}
}
