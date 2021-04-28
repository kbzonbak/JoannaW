package bbr.b2b.regional.logistic.notifications.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.notifications.data.interfaces.INotificationTime;

public class NotificationTimeW extends ElementDTO implements INotificationTime {

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

	public void setHourdate(Date hourdate) {
		this.hourdate = hourdate;
	}

}
