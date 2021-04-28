package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.regional.logistic.buyorders.entities.Discount;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderDiscount;

public class OrderDiscount extends Discount implements IOrderDiscount {

	private Order order;

	public Order getOrder(){ 
		return this.order;
	}
	public void setOrder(Order order){ 
		this.order = order;
	}
}
