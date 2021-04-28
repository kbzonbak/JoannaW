package bbr.b2b.regional.logistic.notifications.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.notifications.data.interfaces.INotificationData;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class NotificationData implements INotificationData {

	private Long id;
	private String number;
	private Date loaddate;
	private NotificationType notificationType;
	private Vendor vendor;

	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Long getId(){ 
		return this.id;
	}
	public String getNumber(){ 
		return this.number;
	}
	public Date getLoaddate(){ 
		return this.loaddate;
	}
	public NotificationType getNotificationType(){ 
		return this.notificationType;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setNumber(String number){ 
		this.number = number;
	}
	public void setLoaddate(Date loaddate){ 
		this.loaddate = loaddate;
	}
	public void setNotificationType(NotificationType notificationType){ 
		this.notificationType = notificationType;
	}
}
