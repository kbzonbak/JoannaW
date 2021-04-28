package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CommuneCourierArrayResultDTO extends BaseResultDTO{
	
	private CommuneCourierDTO[] communeCouriers;

	public CommuneCourierDTO[] getCommuneCouriers() {
		return communeCouriers;
	}
	public void setCommuneCouriers(CommuneCourierDTO[] communeCouriers) {
		this.communeCouriers = communeCouriers;
	}
	
}
