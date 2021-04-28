package bbr.b2b.logistic.notifications.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.notifications.data.interfaces.INotificationTime;

public class NotificationTime implements INotificationTime {

	private Long id;
	private String hourname;
	private LocalDateTime hourdate;
	private Boolean visible;

	public Long getId(){ 
		return this.id;
	}
	public String getHourname(){ 
		return this.hourname;
	}
	public LocalDateTime getHourdate(){ 
		return this.hourdate;
	}
	public Boolean getVisible(){ 
		return this.visible;
	}
	public void setId(Long id){ 
		this.id = id;
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
