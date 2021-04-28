package bbr.b2b.regional.logistic.buyorders.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.buyorders.entities.OrderStateType;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderState;

public class OrderState implements IOrderState {

	private Long id;
	private Date when;
	private Order order;
	private OrderStateType orderstatetype;

	public Long getId(){ 
		return this.id;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public Order getOrder(){ 
		return this.order;
	}
	public OrderStateType getOrderstatetype(){ 
		return this.orderstatetype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setOrder(Order order){ 
		this.order = order;
	}
	public void setOrderstatetype(OrderStateType orderstatetype){ 
		this.orderstatetype = orderstatetype;
	}
}
