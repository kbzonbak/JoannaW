package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class OrderDiscountArrayResultDTO extends BaseResultDTO {

	private OrderDiscountDTO[] orderdiscount;

	public OrderDiscountDTO[] getOrderdiscount() {
		return orderdiscount;
	}

	public void setOrderdiscount(OrderDiscountDTO[] orderdiscount) {
		this.orderdiscount = orderdiscount;
	}
	
}
