package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVPDExcelOrderReportResultDTO extends BaseResultDTO {

	private VeVPDExcelOrderReportDataDTO[] excelOrders = null;
	private VeVPDOrderReportDataDTO[] orders = null;
	private int totalRows;
	
	public VeVPDExcelOrderReportDataDTO[] getExcelOrders() {
		return excelOrders;
	}
	public void setExcelOrders(VeVPDExcelOrderReportDataDTO[] excelOrders) {
		this.excelOrders = excelOrders;
	}
	public VeVPDOrderReportDataDTO[] getOrders() {
		return orders;
	}
	public void setOrders(VeVPDOrderReportDataDTO[] orders) {
		this.orders = orders;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
}
