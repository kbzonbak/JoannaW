package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrOrderExcelReportResultDTO extends BaseResultDTO {

	private DvrOrderExcelReportDataDTO[] dvrorders;

	public DvrOrderExcelReportDataDTO[] getDvrorders() {
		return dvrorders;
	}

	public void setDvrorders(DvrOrderExcelReportDataDTO[] dvrorders) {
		this.dvrorders = dvrorders;
	}

}
