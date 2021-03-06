package bbr.b2b.extractors.ripley.dto;

public class ClientePurchaseDTO {
	private String order_id;
	private String order_type;
	private String order_status;
	private String order_ticket_number;
	private String dispatch_order_number;
	private String order_sale_date;
	private String order_billing_date;
	private String order_delivery_id;
	private String order_delivery_date;
	private String order_delivery_place;
	private String order_delivery_address;
	private String order_store_code;
	private String order_store_name;
	private String order_client_obs;
	private String delivery_warehouse_code;
	private String delivery_warehouse_name;
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getOrder_ticket_number() {
		return order_ticket_number;
	}
	public void setOrder_ticket_number(String order_ticket_number) {
		this.order_ticket_number = order_ticket_number;
	}
	public String getDispatch_order_number() {
		return dispatch_order_number;
	}
	public void setDispatch_order_number(String dispatch_order_number) {
		this.dispatch_order_number = dispatch_order_number;
	}
	public String getOrder_sale_date() {
		return order_sale_date;
	}
	public void setOrder_sale_date(String order_sale_date) {
		this.order_sale_date = order_sale_date;
	}
	public String getOrder_billing_date() {
		return order_billing_date;
	}
	public void setOrder_billing_date(String order_billing_date) {
		this.order_billing_date = order_billing_date;
	}
	public String getOrder_delivery_id() {
		return order_delivery_id;
	}
	public void setOrder_delivery_id(String order_delivery_id) {
		this.order_delivery_id = order_delivery_id;
	}
	public String getOrder_delivery_date() {
		return order_delivery_date;
	}
	public void setOrder_delivery_date(String order_delivery_date) {
		this.order_delivery_date = order_delivery_date;
	}
	public String getOrder_delivery_place() {
		return order_delivery_place;
	}
	public void setOrder_delivery_place(String order_delivery_place) {
		this.order_delivery_place = order_delivery_place;
	}
	public String getOrder_delivery_address() {
		return order_delivery_address;
	}
	public void setOrder_delivery_address(String order_delivery_address) {
		this.order_delivery_address = order_delivery_address;
	}
	public String getOrder_store_code() {
		return order_store_code;
	}
	public void setOrder_store_code(String order_store_code) {
		this.order_store_code = order_store_code;
	}
	public String getOrder_client_obs() {
		return order_client_obs;
	}
	public void setOrder_client_obs(String order_client_obs) {
		this.order_client_obs = order_client_obs;
	}
	public String getDelivery_warehouse_code() {
		return delivery_warehouse_code;
	}
	public void setDelivery_warehouse_code(String delivery_warehouse_code) {
		this.delivery_warehouse_code = delivery_warehouse_code;
	}
	public String getDelivery_warehouse_name() {
		return delivery_warehouse_name;
	}
	public void setDelivery_warehouse_name(String delivery_warehouse_name) {
		this.delivery_warehouse_name = delivery_warehouse_name;
	}
	public String getOrder_store_name() {
		return order_store_name;
	}
	public void setOrder_store_name(String order_store_name) {
		this.order_store_name = order_store_name;
	}
}
