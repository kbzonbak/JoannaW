package bbr.b2b.logistic.notifications.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class NotificationTimeArrayDTO extends BaseResultDTO {

	private NotificationTimeDTO[] notificationTime;

	public NotificationTimeDTO[] getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(NotificationTimeDTO[] notificationTime) {
		this.notificationTime = notificationTime;
	}
}
