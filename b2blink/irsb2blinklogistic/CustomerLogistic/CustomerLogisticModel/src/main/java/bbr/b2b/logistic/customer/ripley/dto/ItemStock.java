package bbr.b2b.logistic.customer.ripley.dto;

public class ItemStock {

	private String warehouse;
	private String sku;
	private String base_stock;
	private String exception_stock;
	private String exception_start_date;
	private String exception_end_date;
	
	public ItemStock(String warehouse, String sku, String base_stock, String exception_stock, String exception_start_date, String exception_end_date) {
		super();
		this.warehouse = warehouse;
		this.sku = sku;
		this.base_stock = base_stock;
		this.exception_stock = exception_stock;
		this.exception_start_date = exception_start_date;
		this.exception_end_date = exception_end_date;
	}
	
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getBase_stock() {
		return base_stock;
	}
	public void setBase_stock(String base_stock) {
		this.base_stock = base_stock;
	}
	public String getException_stock() {
		return exception_stock;
	}
	public void setException_stock(String exception_stock) {
		this.exception_stock = exception_stock;
	}
	public String getException_start_date() {
		return exception_start_date;
	}
	public void setException_start_date(String exception_start_date) {
		this.exception_start_date = exception_start_date;
	}
	public String getException_end_date() {
		return exception_end_date;
	}
	public void setException_end_date(String exception_end_date) {
		this.exception_end_date = exception_end_date;
	}
}
