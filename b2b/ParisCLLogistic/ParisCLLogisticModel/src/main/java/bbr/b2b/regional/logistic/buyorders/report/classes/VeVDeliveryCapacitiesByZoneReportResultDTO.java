package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVDeliveryCapacitiesByZoneReportResultDTO extends BaseResultDTO {

	private VeVDeliveryCapacitiesByZoneReportDataDTO[] capacityreport = null;

	private BaseResultDTO[] validationerrors;

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

	public VeVDeliveryCapacitiesByZoneReportDataDTO[] getCapacityreport() {
		return capacityreport;
	}

	public void setCapacityreport(VeVDeliveryCapacitiesByZoneReportDataDTO[] capacityreport) {
		this.capacityreport = capacityreport;
	}
}
