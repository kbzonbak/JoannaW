package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ExcelDODeliveryReportResultDTO extends BaseResultDTO {

	private ExcelDODeliveryReportDataDTO[] exceldeliverys = null;
	private int totalRows;
	
	public ExcelDODeliveryReportDataDTO[] getExceldeliverys() {
		return exceldeliverys;
	}
	public void setExceldeliverys(ExcelDODeliveryReportDataDTO[] exceldeliverys) {
		this.exceldeliverys = exceldeliverys;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
}
