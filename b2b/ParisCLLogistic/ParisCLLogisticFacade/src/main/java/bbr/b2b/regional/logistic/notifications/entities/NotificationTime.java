package bbr.b2b.regional.logistic.notifications.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.notifications.data.interfaces.INotificationTime;

public class NotificationTime implements INotificationTime {

	private Long id;
	private String hourname;
	private Date hourdate;

	public String getHourname() {
		return hourname;
	}

	public void setHourname(String hourname) {
		this.hourname = hourname;
	}

	public Date getHourdate() {
		return hourdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHourdate(Date hourdate) {
		this.hourdate = hourdate;
	}

}
