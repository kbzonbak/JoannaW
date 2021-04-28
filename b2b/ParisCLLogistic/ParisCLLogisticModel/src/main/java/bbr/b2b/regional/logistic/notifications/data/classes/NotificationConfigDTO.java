package bbr.b2b.regional.logistic.notifications.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class NotificationConfigDTO extends BaseResultDTO {

	private String vendorcode;
	private Long[] notificationTypeAssigned;
	private Long[] hoursAssigned;

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

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}



}
