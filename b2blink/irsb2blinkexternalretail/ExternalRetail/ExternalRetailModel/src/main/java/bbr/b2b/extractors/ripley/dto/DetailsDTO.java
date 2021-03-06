package bbr.b2b.extractors.ripley.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class DetailsDTO {
	private String item;
	private String ripley_sku;
	private String vendor_product_code;
	@JsonProperty(value="ripley _ean13")
	private String ripley_ean13;
	private String vendor_ean13;
	private String item_department_code;
	private String item_line_code;
	private String item_description;
	private Double item_base_cost;
	private String item_sale_price;
	private String item_quantity;
	private Double item_net_cost;
	private String item_charge;
	private String item_discount;
	private String item_lpn;
	private String item_obs;
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getRipley_sku() {
		return ripley_sku;
	}
	public void setRipley_sku(String ripley_sku) {
		this.ripley_sku = ripley_sku;
	}
	public String getVendor_product_code() {
		return vendor_product_code;
	}
	public void setVendor_product_code(String vendor_product_code) {
		this.vendor_product_code = vendor_product_code;
	}
	public String getRipleyEan13() {
		return ripley_ean13;
	}
	public void setRipleyEan13(String ripley) {
		this.ripley_ean13 = ripley;
	}
	public String getVendor_ean13() {
		return vendor_ean13;
	}
	public void setVendor_ean13(String vendor_ean13) {
		this.vendor_ean13 = vendor_ean13;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public Double getItem_base_cost() {
		return item_base_cost;
	}
	public void setItem_base_cost(Double item_base_cost) {
		this.item_base_cost = item_base_cost;
	}
	public String getItem_sale_price() {
		return item_sale_price;
	}
	public void setItem_sale_price(String item_sale_price) {
		this.item_sale_price = item_sale_price;
	}
	public String getItem_quantity() {
		return item_quantity;
	}
	public void setItem_quantity(String item_quantity) {
		this.item_quantity = item_quantity;
	}
	public Double getItem_net_cost() {
		return item_net_cost;
	}
	public void setItem_net_cost(Double item_net_cost) {
		this.item_net_cost = item_net_cost;
	}
	public String getItem_charge() {
		return item_charge;
	}
	public void setItem_charge(String item_charge) {
		this.item_charge = item_charge;
	}
	public String getItem_discount() {
		return item_discount;
	}
	public void setItem_discount(String item_discount) {
		this.item_discount = item_discount;
	}
	public String getItem_lpn() {
		return item_lpn;
	}
	public void setItem_lpn(String item_lpn) {
		this.item_lpn = item_lpn;
	}
	public String getItem_obs() {
		return item_obs;
	}
	public void setItem_obs(String item_obs) {
		this.item_obs = item_obs;
	}
	public String getRipley_ean13() {
		return ripley_ean13;
	}
	public void setRipley_ean13(String ripley_ean13) {
		this.ripley_ean13 = ripley_ean13;
	}
	public String getItem_department_code() {
		return item_department_code;
	}
	public void setItem_department_code(String item_department_code) {
		this.item_department_code = item_department_code;
	}
	public String getItem_line_code() {
		return item_line_code;
	}
	public void setItem_line_code(String item_line_code) {
		this.item_line_code = item_line_code;
	}
}
