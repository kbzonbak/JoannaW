package bbr.b2b.logistic.ddcorders.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderState;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderStateType;

public class DdcOrderState implements IDdcOrderState {

	private Long id;
	private LocalDateTime when;
	private String who;
	private String comment;
	private DdcOrder ddcorder;
	private DdcOrderStateType ddcorderstatetype;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public DdcOrder getDdcorder() {
		return ddcorder;
	}
	public void setDdcorder(DdcOrder ddcorder) {
		this.ddcorder = ddcorder;
	}
	public DdcOrderStateType getDdcorderstatetype() {
		return ddcorderstatetype;
	}
	public void setDdcorderstatetype(DdcOrderStateType ddcorderstatetype) {
		this.ddcorderstatetype = ddcorderstatetype;
	}

}
