package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DatingTimeDockDataDTO implements Serializable {

	private String dockname;
	private LocalDateTime starttime;
	private LocalDateTime endtime;
	private Long intervalhour;
	private Long intervalminute;

	public String getDockname() {
		return dockname;
	}

	public void setDockname(String dockname) {
		this.dockname = dockname;
	}

	public LocalDateTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalDateTime starttime) {
		this.starttime = starttime;
	}

	public LocalDateTime getEndtime() {
		return endtime;
	}

	public void setEndtime(LocalDateTime endtime) {
		this.endtime = endtime;
	}

	public Long getIntervalhour() {
		return intervalhour;
	}

	public void setIntervalhour(Long intervalhour) {
		this.intervalhour = intervalhour;
	}

	public Long getIntervalminute() {
		return intervalminute;
	}

	public void setIntervalminute(Long intervalminute) {
		this.intervalminute = intervalminute;
	}

}
