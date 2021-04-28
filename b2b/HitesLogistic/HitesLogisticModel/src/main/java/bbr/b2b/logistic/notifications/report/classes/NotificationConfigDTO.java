package bbr.b2b.logistic.notifications.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class NotificationConfigDTO extends BaseResultDTO {

	private Long contactid;
	private Long[] notificationTypeAssigned;
	private Long[] hoursAssigned;

	public Long getContactid() {
		return contactid;
	}

	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}

	public Long[] getNotificationTypeAssigned() {
		return notificationTypeAssigned;
	}

	public void setNotificationTypeAssigned(Long[] notificationTypeAssigned) {
		this.notificationTypeAssigned = notificationTypeAssigned;
	}

	public Long[] getHoursAssigned() {
		return hoursAssigned;
	}

	public void setHoursAssigned(Long[] hoursAssigned) {
		this.hoursAssigned = hoursAssigned;
	}

}
