package bbr.b2b.regional.logistic.notifications.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.notifications.data.interfaces.INotificationData;

public class NotificationDataW extends ElementDTO implements INotificationData {

	private String number;
	private Date loaddate;
	private Long notificationtypeid;
	private Long vendorid;

	public String getNumber(){ 
		return this.number;
	}
	public Date getLoaddate(){ 
		return this.loaddate;
	}
	public Long getNotificationtypeid(){ 
		return this.notificationtypeid;
	}
	public void setNumber(String number){ 
		this.number = number;
	}
	public void setLoaddate(Date loaddate){ 
		this.loaddate = loaddate;
	}
	public void setNotificationtypeid(Long notificationtypeid){ 
		this.notificationtypeid = notificationtypeid;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
}
