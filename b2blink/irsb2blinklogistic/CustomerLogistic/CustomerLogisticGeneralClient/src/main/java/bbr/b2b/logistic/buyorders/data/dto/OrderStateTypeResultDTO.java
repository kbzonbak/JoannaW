package bbr.b2b.logistic.buyorders.data.dto;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class OrderStateTypeResultDTO extends BaseResultDTO {

	private OrderStateTypeDTO[] orderstatetype;

	public OrderStateTypeDTO[] getOrderstatetype() {
		return orderstatetype;
	}

	public void setOrderstatetype(OrderStateTypeDTO[] orderstatetype) {
		this.orderstatetype = orderstatetype;
	}

	

}
