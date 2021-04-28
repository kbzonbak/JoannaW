package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class OrderContainerDataResultDTO extends BaseResultDTO {

	private OrderContainerDataDTO[] ordercontainers;

	public OrderContainerDataDTO[] getOrdercontainers() {
		return ordercontainers;
	}

	public void setOrdercontainers(OrderContainerDataDTO[] ordercontainers) {
		this.ordercontainers = ordercontainers;
	}
		
}
