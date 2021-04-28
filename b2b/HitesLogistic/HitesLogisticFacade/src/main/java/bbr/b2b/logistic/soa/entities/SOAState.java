package bbr.b2b.logistic.soa.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.buyorders.entities.Order;
import bbr.b2b.logistic.soa.data.interfaces.ISOAState;

public class SOAState implements ISOAState {

	private Long id;
	private LocalDateTime when;
	private String comment;
	private Order order;
	private SOAStateType soastatetype;
	
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