package bbr.b2b.regional.logistic.datings.report.classes;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ExcelDatingRequestReportDataDTO  extends BaseResultDTO {

	public ExcelDatingRequestReportData[] excelDODatingReportData = null;

	public ExcelDatingRequestReportData[] getExcelDODatingReportData() {
		return excelDODatingReportData;
	}

	public void setExcelDODatingReportData(ExcelDatingRequestReportData[] excelDODatingReportData) {
		this.excelDODatingReportData = excelDODatingReportData;
	}


}
