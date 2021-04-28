package bbr.b2b.regional.logistic.notifications.entities;

import bbr.b2b.regional.logistic.notifications.data.interfaces.INotification;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class Notification implements INotification {

	private NotificationId id;
	private Vendor vendor;
	private NotificationType notificationtype;
	private NotificationTime notificationtime;

	public NotificationId getId(){ 
		return this.id;
	}
	public NotificationType getNotificationtype(){ 
		return this.notificationtype;
	}
	public NotificationTime getNotificationtime(){ 
		return this.notificationtime;
	}
	public void setId(NotificationId id){ 
		this.id = id;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public void setNotificationtype(NotificationType notificationtype){ 
		this.notificationtype = notificationtype;
	}
	public void setNotificationtime(NotificationTime notificationtime){ 
		this.notificationtime = notificationtime;
	}
}
