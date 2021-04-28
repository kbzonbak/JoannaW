package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVAvailableStockReportDataDTO implements Serializable{

	private String itemsku;
	private String vendorcode;
	private String itemdescription;
	private Integer stock;
	private Integer stockoriginal;	//stock enviado por el cliente
	
	public Integer getStockoriginal() {
		return stockoriginal;
	}
	public void setStockoriginal(Integer stockoriginal) {
		this.stockoriginal = stockoriginal;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getItemdescription() {
		return itemdescription;
	}
	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}	
}
