package bbr.b2b.logistic.ddcdeliveries.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDeliveryState;

public class DdcDeliveryState implements IDdcDeliveryState {

	private Long id;
	private LocalDateTime when;
	private String username;
	private String usertype;
	private LocalDateTime statedate;
	private String comment;
	private DdcDelivery ddcdelivery;
	private DdcDeliveryStateType ddcdeliverystatetype;
	
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
	public DdcDelivery getDdcdelivery() {
		return ddcdelivery;
	}
	public void setDdcdelivery(DdcDelivery ddcdelivery) {
		this.ddcdelivery = ddcdelivery;
	}
	public DdcDeliveryStateType getDdcdeliverystatetype() {
		return ddcdeliverystatetype;
	}
	public void setDdcdeliverystatetype(DdcDeliveryStateType ddcdeliverystatetype) {
		this.ddcdeliverystatetype = ddcdeliverystatetype;
	}
	
}
