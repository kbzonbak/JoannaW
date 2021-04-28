package bbr.b2b.logistic.vev.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class StockWSReportDataDTO extends BaseResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StockWSDTO stockwsdata;
		
	public StockWSDTO getStockwsdata() {
		return stockwsdata;
	}
	public void setStockwsdata(StockWSDTO stockwsdata) {
		this.stockwsdata = stockwsdata;
	}
}
