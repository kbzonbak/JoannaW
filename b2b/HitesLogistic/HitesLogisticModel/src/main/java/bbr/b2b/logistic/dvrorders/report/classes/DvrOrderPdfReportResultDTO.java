package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrOrderPdfReportResultDTO extends BaseResultDTO {

	private DvrOrderPdfReportDataDTO[] dvrorders;

	public DvrOrderPdfReportDataDTO[] getDvrorders() {
		return dvrorders;
	}

	public void setDvrorders(DvrOrderPdfReportDataDTO[] dvrorders) {
		this.dvrorders = dvrorders;
	}
}
