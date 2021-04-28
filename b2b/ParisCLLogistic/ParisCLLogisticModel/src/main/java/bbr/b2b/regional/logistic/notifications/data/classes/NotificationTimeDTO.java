package bbr.b2b.regional.logistic.notifications.data.classes;

import java.io.Serializable;

public class NotificationTimeDTO implements Serializable {

	private Long id;
	private String hourname;

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

}
