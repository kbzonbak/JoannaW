package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportDataDTO;

public class AddDatingRequestResultDTO extends BaseResultDTO {

	private BaseResultDTO[] validationerrors;
	private DeliveryReportDataDTO report;
	
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}
	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
	public DeliveryReportDataDTO getReport() {
		return report;
	}
	public void setReport(DeliveryReportDataDTO report) {
		this.report = report;
	}	
}
