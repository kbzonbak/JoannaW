package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class RescheduleReasonArrayResultDTO extends BaseResultDTO {

	private RescheduleReasonDTO[] reschedulereasons;

	public RescheduleReasonDTO[] getReschedulereasons() {
		return reschedulereasons;
	}

	public void setReschedulereasons(RescheduleReasonDTO[] reschedulereasons) {
		this.reschedulereasons = reschedulereasons;
	}
		
}
