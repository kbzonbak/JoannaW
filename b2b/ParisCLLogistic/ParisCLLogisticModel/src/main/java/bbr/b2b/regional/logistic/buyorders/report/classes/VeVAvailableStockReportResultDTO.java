package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVAvailableStockReportResultDTO extends BaseResultDTO{

	private VeVAvailableStockReportDataDTO[] stockreport = null;
	private BaseResultDTO[] validationerrors;
	private VeVAvailableStockReportResultByItemDTO[] stockreportbyitem = null;
	
	public VeVAvailableStockReportResultByItemDTO[] getStockreportbyitem() {
		return stockreportbyitem;
	}

	public void setStockreportbyitem(VeVAvailableStockReportResultByItemDTO[] stockreportbyitem) {
		this.stockreportbyitem = stockreportbyitem;
	}

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

	public VeVAvailableStockReportDataDTO[] getStockreport() {
		return stockreport;
	}

	public void setStockreport(VeVAvailableStockReportDataDTO[] stockreport) {
		this.stockreport = stockreport;
	}	
}
