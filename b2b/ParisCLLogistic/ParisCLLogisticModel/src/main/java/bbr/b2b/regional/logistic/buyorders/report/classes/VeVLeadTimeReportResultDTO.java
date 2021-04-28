package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVLeadTimeReportResultDTO extends BaseResultDTO{

	private VeVLeadTimeReportDataDTO[] leadtimereport = null;
	private BaseResultDTO[] validationerrors;
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

	public VeVLeadTimeReportDataDTO[] getLeadtimereport() {
		return leadtimereport;
	}

	public void setLeadtimereport(VeVLeadTimeReportDataDTO[] leadtimereport) {
		this.leadtimereport = leadtimereport;
	}
}
