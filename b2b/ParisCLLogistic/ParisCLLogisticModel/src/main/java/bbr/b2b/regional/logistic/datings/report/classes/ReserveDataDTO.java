package bbr.b2b.regional.logistic.datings.report.classes;

import java.time.LocalDateTime;

public class ReserveDataDTO {
	
	private String dockcode;
	private LocalDateTime start;
	private LocalDateTime end;
	
	
	public String getDockcode() {
		return dockcode;
	}
	public void setDockcode(String dockcode) {
		this.dockcode = dockcode;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

}
