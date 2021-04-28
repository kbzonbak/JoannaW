package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class HiredCourierResultDTO extends BaseResultDTO {

	private HiredCourierDTO hiredCourier;

	public HiredCourierDTO getHiredCourier() {
		return hiredCourier;
	}

	public void setHiredCourier(HiredCourierDTO hiredCourier) {
		this.hiredCourier = hiredCourier;
	}
}
