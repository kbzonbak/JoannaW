package bbr.b2b.logistic.notifications.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NotificationCSVDTO implements Serializable {

	private String event;
	private String numberid;
	private LocalDateTime when1;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getNumberid() {
		return numberid;
	}

	public void setNumberid(String numberid) {
		this.numberid = numberid;
	}

	public LocalDateTime getWhen1() {
		return when1;
	}

	public void setWhen1(LocalDateTime when1) {
		this.when1 = when1;
	}

}
