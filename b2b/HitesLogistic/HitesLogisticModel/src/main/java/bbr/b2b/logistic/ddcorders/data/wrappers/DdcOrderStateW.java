package bbr.b2b.logistic.ddcorders.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderState;

public class DdcOrderStateW extends ElementDTO implements IDdcOrderState {

	private LocalDateTime when;
	private String who;
	private String comment;
	private Long ddcorderid;
	private Long ddcorderstatetypeid;
	
	public LocalDateTime getWhen() {
		return when;
	}
	public void setWhen(LocalDateTime when) {
		this.when = when;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getDdcorderid() {
		return ddcorderid;
	}
	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
	}
	public Long getDdcorderstatetypeid() {
		return ddcorderstatetypeid;
	}
	public void setDdcorderstatetypeid(Long ddcorderstatetypeid) {
		this.ddcorderstatetypeid = ddcorderstatetypeid;
	}

}
