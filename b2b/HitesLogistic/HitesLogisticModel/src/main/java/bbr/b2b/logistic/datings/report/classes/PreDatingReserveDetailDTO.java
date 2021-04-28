package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PreDatingReserveDetailDTO implements Serializable{

	private Long predatingresourcegroupid;
	private Long reserveid;
	private LocalDateTime when;
	private Long dockid;
	private String dockcode;
	private Long moduleid;
	private LocalDateTime starts;
	private LocalDateTime ends;
	
	public Long getPredatingresourcegroupid() {
		return predatingresourcegroupid;
	}
	public void setPredatingresourcegroupid(Long predatingresourcegroupid) {
		this.predatingresourcegroupid = predatingresourcegroupid;
	}
	public Long getReserveid() {
		return reserveid;
	}
	public void setReserveid(Long reserveid) {
		this.reserveid = reserveid;
	}
	public LocalDateTime getWhen() {
		return when;
	}
	public void setWhen(LocalDateTime when) {
		this.when = when;
	}
	public Long getDockid() {
		return dockid;
	}
	public void setDockid(Long dockid) {
		this.dockid = dockid;
	}
	public String getDockcode() {
		return dockcode;
	}
	public void setDockcode(String dockcode) {
		this.dockcode = dockcode;
	}
	public Long getModuleid() {
		return moduleid;
	}
	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}
	public LocalDateTime getStarts() {
		return starts;
	}
	public void setStarts(LocalDateTime starts) {
		this.starts = starts;
	}
	public LocalDateTime getEnds() {
		return ends;
	}
	public void setEnds(LocalDateTime ends) {
		this.ends = ends;
	}
		
}
