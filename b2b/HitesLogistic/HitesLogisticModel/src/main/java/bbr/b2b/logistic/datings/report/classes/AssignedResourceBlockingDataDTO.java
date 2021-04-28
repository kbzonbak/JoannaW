package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AssignedResourceBlockingDataDTO implements Serializable {

	private Long moduleid;
	private Long dockid;
	private Long reserveid;
	private Long blockingroupid;
	private String who;
	private LocalDateTime created;
	private String comment;
	private String reason;

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

	public Long getBlockingroupid() {
		return blockingroupid;
	}

	public void setBlockingroupid(Long blockingroupid) {
		this.blockingroupid = blockingroupid;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
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

}
