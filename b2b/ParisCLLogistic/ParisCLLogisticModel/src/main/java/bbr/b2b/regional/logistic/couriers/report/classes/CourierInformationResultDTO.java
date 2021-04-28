package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CourierInformationResultDTO extends BaseResultDTO {
	
	private CourierInformationDTO[] courierinformation;

	public CourierInformationDTO[] getCourierinformation() {
		return courierinformation;
	}

	public void setCourierinformation(CourierInformationDTO[] courierinformation) {
		this.courierinformation = courierinformation;
	}

}
