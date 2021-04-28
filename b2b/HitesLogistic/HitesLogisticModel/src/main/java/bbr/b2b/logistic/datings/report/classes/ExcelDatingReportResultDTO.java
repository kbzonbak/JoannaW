package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ExcelDatingReportResultDTO extends BaseResultDTO {

	private ExcelDatingReportDataDTO[] exceldatingreportdata;

	public ExcelDatingReportDataDTO[] getExceldatingreportdata() {
		return exceldatingreportdata;
	}

	public void setExceldatingreportdata(ExcelDatingReportDataDTO[] exceldatingreportdata) {
		this.exceldatingreportdata = exceldatingreportdata;
	}

}
