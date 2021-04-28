package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CourierResultDTO extends BaseResultDTO{
	
	private CourierDTO[] couriers;

	public CourierDTO[] getCouriers() {
		return couriers;
	}
	public void setCouriers(CourierDTO[] couriers) {
		this.couriers = couriers;
	}
	
}
