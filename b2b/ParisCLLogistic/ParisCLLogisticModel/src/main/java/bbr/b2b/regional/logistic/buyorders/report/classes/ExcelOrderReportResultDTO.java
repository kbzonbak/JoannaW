package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ExcelOrderReportResultDTO extends BaseResultDTO {

	private ExcelOrderReportDataDTO[] excelOrders = null;
	private OrderReportDataDTO[] orders = null;
	private int totalRows;
	private Boolean retailImported;
	
	public ExcelOrderReportDataDTO[] getExcelOrders() {
		return excelOrders;
	}

	public void setExcelOrders(ExcelOrderReportDataDTO[] excelOrders) {
		this.excelOrders = excelOrders;
	}	

	public OrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(OrderReportDataDTO[] orders) {
		this.orders = orders;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public Boolean getRetailImported() {
		return retailImported;
	}

	public void setRetailImported(Boolean retailImported) {
		this.retailImported = retailImported;
	}
	
}
