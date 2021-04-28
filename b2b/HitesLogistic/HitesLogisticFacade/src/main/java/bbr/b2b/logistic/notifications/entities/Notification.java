package bbr.b2b.logistic.notifications.entities;

import bbr.b2b.logistic.notifications.entities.Contact;
import bbr.b2b.logistic.notifications.entities.NotificationType;
import bbr.b2b.logistic.notifications.entities.NotificationTime;
import bbr.b2b.logistic.notifications.data.interfaces.INotification;

public class Notification implements INotification {

	private NotificationId id;
	private Contact contact;
	private NotificationType notificationtype;
	private NotificationTime notificationtime;

	public NotificationId getId(){ 
		return this.id;
	}
	public Contact getContact(){ 
		return this.contact;
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
	public void setContact(Contact contact){ 
		this.contact = contact;
	}
	public void setNotificationtype(NotificationType notificationtype){ 
		this.notificationtype = notificationtype;
	}
	public void setNotificationtime(NotificationTime notificationtime){ 
		this.notificationtime = notificationtime;
	}
}
