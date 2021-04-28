package bbr.b2b.logistic.notifications.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.notifications.data.interfaces.INotificationData;
import bbr.b2b.logistic.vendors.entities.Vendor;

public class NotificationData implements INotificationData {

	private Long id;
	private String number;
	private LocalDateTime loaddate;
	private NotificationType notificationType;
	private Vendor vendor;

	public Long getId(){ 
		return this.id;
	}
	public String getNumber(){ 
		return this.number;
	}
	public LocalDateTime getLoaddate(){ 
		return this.loaddate;
	}
	public NotificationType getNotificationType(){ 
		return this.notificationType;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setNumber(String number){ 
		this.number = number;
	}
	public void setLoaddate(LocalDateTime loaddate){ 
		this.loaddate = loaddate;
	}
	public void setNotificationType(NotificationType notificationType){ 
		this.notificationType = notificationType;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
}
