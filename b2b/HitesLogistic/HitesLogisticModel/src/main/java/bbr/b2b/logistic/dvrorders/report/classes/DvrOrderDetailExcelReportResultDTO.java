package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrOrderDetailExcelReportResultDTO extends BaseResultDTO {

	private DvrOrderDetailExcelReportDataDTO[] dvrorderdetails;

	public DvrOrderDetailExcelReportDataDTO[] getDvrorderdetails() {
		return dvrorderdetails;
	}

	public void setDvrorderdetails(DvrOrderDetailExcelReportDataDTO[] dvrorderdetails) {
		this.dvrorderdetails = dvrorderdetails;
	}
}