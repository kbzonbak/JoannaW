package bbr.b2b.regional.logistic.soa.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.soa.data.interfaces.ISOAState;

public class SOAState implements ISOAState {

	private Long id;
	private Date when;
	private String comment;
	private Order order;
	private SOAStateType soastatetype;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public SOAStateType getSoastatetype() {
		return soastatetype;
	}
	public void setSoastatetype(SOAStateType soastatetype) {
		this.soastatetype = soastatetype;
	}	
}