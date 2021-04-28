package bbr.b2b.logistic.ddcdeliveries.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDeliveryState;

public class DdcDeliveryStateW extends ElementDTO implements IDdcDeliveryState {

	private LocalDateTime when;
	private String username;
	private String usertype;
	private LocalDateTime statedate;
	private String comment;
	private Long ddcdeliveryid;
	private Long ddcdeliverystatetypeid;
	
	public LocalDateTime getWhen() {
		return when;
	}
	public void setWhen(LocalDateTime when) {
		this.when = when;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public LocalDateTime getStatedate() {
		return statedate;
	}
	public void setStatedate(LocalDateTime statedate) {
		this.statedate = statedate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getDdcdeliveryid() {
		return ddcdeliveryid;
	}
	public void setDdcdeliveryid(Long ddcdeliveryid) {
		this.ddcdeliveryid = ddcdeliveryid;
	}
	public Long getDdcdeliverystatetypeid() {
		return ddcdeliverystatetypeid;
	}
	public void setDdcdeliverystatetypeid(Long ddcdeliverystatetypeid) {
		this.ddcdeliverystatetypeid = ddcdeliverystatetypeid;
	}

}
