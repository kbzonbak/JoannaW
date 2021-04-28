package bbr.b2b.logistic.vev.report.classes;

import java.io.Serializable;

public class StockWSDTO implements Serializable {

	private String vendorcde;
	private StockDetailWSDTO[] stockdetailws;
	
	public String getVendorcde() {
		return vendorcde;
	}
	public void setVendorcde(String vendorcde) {
		this.vendorcde = vendorcde;
	}
	public StockDetailWSDTO[] getStockdetailws() {
		return stockdetailws;
	}
	public void setStockdetailws(StockDetailWSDTO[] stockdetailws) {
		this.stockdetailws = stockdetailws;
	}
	
}