package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DdcOrderExcelReportResultDTO extends BaseResultDTO {

	private DdcOrderExcelReportDataDTO[] ddcorders;

	public DdcOrderExcelReportDataDTO[] getDdcorders() {
		return ddcorders;
	}

	public void setDdcorders(DdcOrderExcelReportDataDTO[] ddcorders) {
		this.ddcorders = ddcorders;
	}

}
