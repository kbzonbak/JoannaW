package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class OrderDeliveryDetailResultDTO extends BaseResultDTO{

	private OrderDeliveryDetailDTO orderdeliverydetail;

	public OrderDeliveryDetailDTO getOrderdeliverydetail() {
		return orderdeliverydetail;
	}

	public void setOrderdeliverydetail(OrderDeliveryDetailDTO orderdeliverydetail) {
		this.orderdeliverydetail = orderdeliverydetail;
	}
}
