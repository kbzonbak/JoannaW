package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVDeliveryTimesReportResultDTO extends BaseResultDTO {

	private VeVDeliveryTimesReportDataDTO[] timesreport = null;

	private BaseResultDTO[] validationerrors;

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

	public VeVDeliveryTimesReportDataDTO[] getTimesreport() {
		return timesreport;
	}

	public void setTimesreport(VeVDeliveryTimesReportDataDTO[] timesreport) {
		this.timesreport = timesreport;
	}
}
