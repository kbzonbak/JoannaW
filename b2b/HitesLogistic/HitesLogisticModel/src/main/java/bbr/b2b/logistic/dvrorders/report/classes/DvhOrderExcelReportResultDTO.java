package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvhOrderExcelReportResultDTO extends BaseResultDTO {

	private DvhOrderExcelReportDataDTO[] dvhorders;

	public DvhOrderExcelReportDataDTO[] getDvhorders() {
		return dvhorders;
	}

	public void setDvhorders(DvhOrderExcelReportDataDTO[] dvhorders) {
		this.dvhorders = dvhorders;
	}
}
