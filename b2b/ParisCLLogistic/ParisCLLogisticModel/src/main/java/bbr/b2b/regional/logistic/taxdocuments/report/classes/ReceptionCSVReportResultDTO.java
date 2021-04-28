package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ReceptionCSVReportResultDTO extends BaseResultDTO {

	private ReceptionCSVReportDataDTO[] receptions;

	public ReceptionCSVReportDataDTO[] getReceptions() {
		return receptions;
	}

	public void setReceptions(ReceptionCSVReportDataDTO[] receptions) {
		this.receptions = receptions;
	}
}
