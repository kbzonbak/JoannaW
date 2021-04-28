package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;
import java.util.Date;

public class CourierStateReportDTO implements Serializable {
	
	private Date started;
	private String startdate;
	private String description;
	
	public Date getStarted() {
		return started;
	}
	public void setStarted(Date started) {
		this.started = started;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
