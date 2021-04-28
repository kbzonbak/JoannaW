package bbr.b2b.logistic.notifications.report.classes;

import java.io.Serializable;

public class NotificationDataDTO implements Serializable {

	private Long notificationtypeid;
	private String code;
	private String description;
	private Integer totalload;

	public Long getNotificationtypeid() {
		return notificationtypeid;
	}

	public void setNotificationtypeid(Long notificationtypeid) {
		this.notificationtypeid = notificationtypeid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTotalload() {
		return totalload;
	}

	public void setTotalload(Integer totalload) {
		this.totalload = totalload;
	}

}
