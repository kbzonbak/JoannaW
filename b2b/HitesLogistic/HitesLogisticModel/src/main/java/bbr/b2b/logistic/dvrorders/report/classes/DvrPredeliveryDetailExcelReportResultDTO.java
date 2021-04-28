package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrPredeliveryDetailExcelReportResultDTO extends BaseResultDTO {

	private DvrPredeliveryDetailExcelReportDataDTO[] dvrpredeliverydetails;

	public DvrPredeliveryDetailExcelReportDataDTO[] getDvrpredeliverydetails() {
		return dvrpredeliverydetails;
	}

	public void setDvrpredeliverydetails(DvrPredeliveryDetailExcelReportDataDTO[] dvrpredeliverydetails) {
		this.dvrpredeliverydetails = dvrpredeliverydetails;
	}
}
