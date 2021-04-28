package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class DatingDetailDTO implements Serializable {
	
	private String dock;
	private String timeRange;
	private String time;
	
	public String getDock() {
		return dock;
	}
	public void setDock(String dock) {
		this.dock = dock;
	}
	public String getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
