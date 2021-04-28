package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.OrderUnitsDTO;

public class OrderUnitsArrayResultDTO extends BaseResultDTO {

	private OrderUnitsDTO[] orderunits;

	public OrderUnitsDTO[] getOrderunits() {
		return orderunits;
	}

	public void setOrderunits(OrderUnitsDTO[] orderunits) {
		this.orderunits = orderunits;
	}	
}
