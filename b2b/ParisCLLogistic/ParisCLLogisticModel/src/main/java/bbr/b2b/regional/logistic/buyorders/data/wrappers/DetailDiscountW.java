package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.regional.logistic.buyorders.data.interfaces.IDetailDiscount;

public class DetailDiscountW extends DiscountW implements IDetailDiscount {

	private Long orderid;
	private Long itemid;

	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
