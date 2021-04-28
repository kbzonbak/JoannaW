package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVCDExcelOrderReportResultDTO extends BaseResultDTO {

	private VeVCDExcelOrderReportDataDTO[] excelOrders = null;
	private VeVCDOrderReportDataDTO[] orders = null;
	private int totalRows;

	public VeVCDExcelOrderReportDataDTO[] getExcelOrders() {
		return excelOrders;
	}

	public void setExcelOrders(VeVCDExcelOrderReportDataDTO[] excelOrders) {
		this.excelOrders = excelOrders;
	}

	public VeVCDOrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(VeVCDOrderReportDataDTO[] orders) {
		this.orders = orders;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
}
