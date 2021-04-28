package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ReceptionExcelReportResultDTO extends BaseResultDTO {

	private ReceptionExcelReportDataDTO[] receptions;

	public ReceptionExcelReportDataDTO[] getReceptions() {
		return receptions;
	}

	public void setReceptions(ReceptionExcelReportDataDTO[] receptions) {
		this.receptions = receptions;
	}	
}
