package bbr.b2b.logistic.vev.report.classes;

import java.io.Serializable;

public class AvailableStockSendDataWSDTO implements Serializable {

	private String itemcode;
	private Integer dailyrep;
	private Integer stockdisp;
	private String warehouse;
	
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public Integer getDailyrep() {
		return dailyrep;
	}
	public void setDailyrep(Integer dailyrep) {
		this.dailyrep = dailyrep;
	}
	public Integer getStockdisp() {
		return stockdisp;
	}
	public void setStockdisp(Integer stockdisp) {
		this.stockdisp = stockdisp;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	

	
	
	
	
}
