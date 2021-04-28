package bbr.b2b.logistic.notifications.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.notifications.data.interfaces.INotificationTime;

public class NotificationTimeW extends ElementDTO implements INotificationTime {

	private String hourname;
	private LocalDateTime hourdate;
	private Boolean visible;

	public String getHourname(){ 
		return this.hourname;
	}
	public LocalDateTime getHourdate(){ 
		return this.hourdate;
	}
	public Boolean getVisible(){ 
		return this.visible;
	}
	public void setHourname(String hourname){ 
		this.hourname = hourname;
	}
	public void setHourdate(LocalDateTime hourdate){ 
		this.hourdate = hourdate;
	}
	public void setVisible(Boolean visible){ 
		this.visible = visible;
	}
}
