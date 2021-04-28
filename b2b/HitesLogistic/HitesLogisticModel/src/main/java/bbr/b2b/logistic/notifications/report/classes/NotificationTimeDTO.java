package bbr.b2b.logistic.notifications.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NotificationTimeDTO implements Serializable {

	private Long id;
	private String hourname;
	private LocalDateTime hourdate;
	private Boolean visible;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHourname() {
		return hourname;
	}

	public void setHourname(String hourname) {
		this.hourname = hourname;
	}

	public LocalDateTime getHourdate() {
		return hourdate;
	}

	public void setHourdate(LocalDateTime hourdate) {
		this.hourdate = hourdate;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
