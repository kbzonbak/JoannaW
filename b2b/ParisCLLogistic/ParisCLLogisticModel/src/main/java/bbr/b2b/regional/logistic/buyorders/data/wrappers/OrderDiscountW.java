package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderDiscount;

public class OrderDiscountW extends DiscountW implements IOrderDiscount {

	private Long orderid;

	public Long getOrderid(){ 
		return this.orderid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
}
