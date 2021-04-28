package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.regional.logistic.buyorders.entities.Discount;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetail;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IDetailDiscount;

public class DetailDiscount extends Discount implements IDetailDiscount {

	private OrderDetail orderdetail;

	public OrderDetail getOrderdetail(){ 
		return this.orderdetail;
	}
	public void setOrderdetail(OrderDetail orderdetail){ 
		this.orderdetail = orderdetail;
	}
}
