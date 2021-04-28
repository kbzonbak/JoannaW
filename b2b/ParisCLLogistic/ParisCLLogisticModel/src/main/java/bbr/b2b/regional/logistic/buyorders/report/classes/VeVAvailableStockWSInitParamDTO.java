package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVAvailableStockWSInitParamDTO implements Serializable{

	private String itemsku;
	private Integer stock;
	
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
		
}