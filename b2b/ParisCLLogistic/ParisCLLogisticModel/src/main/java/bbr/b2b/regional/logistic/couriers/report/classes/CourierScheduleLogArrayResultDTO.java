package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CourierScheduleLogArrayResultDTO extends BaseResultDTO {

	private CourierScheduleLogDTO[] courierschedulelog;

	public CourierScheduleLogDTO[] getCourierschedulelog() {
		return courierschedulelog;
	}

	public void setCourierschedulelog(CourierScheduleLogDTO[] courierschedulelog) {
		this.courierschedulelog = courierschedulelog;
	}
		
}
