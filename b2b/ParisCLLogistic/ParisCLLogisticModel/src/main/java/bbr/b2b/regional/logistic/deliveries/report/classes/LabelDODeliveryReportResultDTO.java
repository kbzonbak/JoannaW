package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class LabelDODeliveryReportResultDTO extends BaseResultDTO {

	private LabelDODeliveryReportDataDTO[] report;

	public LabelDODeliveryReportDataDTO[] getReport() {
		return report;
	}

	public void setReport(LabelDODeliveryReportDataDTO[] report) {
		this.report = report;
	}	
}
