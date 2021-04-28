package bbr.b2b.regional.logistic.notifications.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class NotificationTypesArrayDTO extends BaseResultDTO {

	private NotificationTypeDTO[] notificationtypes;

	public NotificationTypeDTO[] getNotificationtypes() {
		return notificationtypes;
	}

	public void setNotificationtypes(NotificationTypeDTO[] notificationtypes) {
		this.notificationtypes = notificationtypes;
	}

}
