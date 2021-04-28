package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class OrderStateTypeArrayResultDTO extends BaseResultDTO {

	private OrderStateTypeDTO[] orderstatetypes = null;

	public OrderStateTypeDTO[] getOrderstatetypes() {
		return orderstatetypes;
	}

	public void setOrderstatetypes(OrderStateTypeDTO[] orderstatetypes) {
		this.orderstatetypes = orderstatetypes;
	}

}
