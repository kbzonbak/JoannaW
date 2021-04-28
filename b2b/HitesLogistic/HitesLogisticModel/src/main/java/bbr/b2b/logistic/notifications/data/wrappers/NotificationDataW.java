package bbr.b2b.logistic.notifications.data.wrappers;

import bbr.b2b.logistic.notifications.data.interfaces.INotificationData;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class NotificationDataW extends ElementDTO implements INotificationData {

	private String number;
	private LocalDateTime loaddate;
	private Long notificationtypeid;
	private Long vendorid;

	public String getNumber(){ 
		return this.number;
	}
	public LocalDateTime getLoaddate(){ 
		return this.loaddate;
	}
	public Long getNotificationtypeid(){ 
		return this.notificationtypeid;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public void setNumber(String number){ 
		this.number = number;
	}
	public void setLoaddate(LocalDateTime loaddate){ 
		this.loaddate = loaddate;
	}
	public void setNotificationtypeid(Long notificationtypeid){ 
		this.notificationtypeid = notificationtypeid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
}
