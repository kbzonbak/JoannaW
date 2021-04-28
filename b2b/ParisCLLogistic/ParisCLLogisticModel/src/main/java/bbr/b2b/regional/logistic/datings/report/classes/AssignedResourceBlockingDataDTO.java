package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class AssignedResourceBlockingDataDTO implements Serializable{
	
	private Long moduleid;
	private Long dockid;
	private Long reserveid;
	private Long blockingroupid;
	private String who;
	private String created;
	private String comment;
	private String reason;
	private String location;
	
	public Long getModuleid() {
		return moduleid;
	}
	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}
	public Long getDockid() {
		return dockid;
	}
	public void setDockid(Long dockid) {
		this.dockid = dockid;
	}
	public Long getReserveid() {
		return reserveid;
	}
	public void setReserveid(Long reserveid) {
		this.reserveid = reserveid;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getBlockingroupid() {
		return blockingroupid;
	}
	public void setBlockingroupid(Long blockingroupid) {
		this.blockingroupid = blockingroupid;
	}

}
