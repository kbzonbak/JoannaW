package bbr.b2b.extractors.ripley.dto;

public class PurchaseOrderDTO {

	private String _id;
	private String _vendor_id;
	private String _available;
	private String created_on_utc;
	private ClientePurchaseDTO client_purchase_order;
	private VendorDTO vendor;
	private ClientDTO client;
	private OrderDTO purchase_order;
	private OperativeInfoDTO operative_info;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get_vendor_id() {
		return _vendor_id;
	}
	public void set_vendor_id(String _vendor_id) {
		this._vendor_id = _vendor_id;
	}
	public ClientePurchaseDTO getClient_purchase_order() {
		return client_purchase_order;
	}
	public void setClient_purchase_order(ClientePurchaseDTO client_purchase_order) {
		this.client_purchase_order = client_purchase_order;
	}
	public VendorDTO getVendor() {
		return vendor;
	}
	public void setVendor(VendorDTO vendor) {
		this.vendor = vendor;
	}
	public ClientDTO getClient() {
		return client;
	}
	public void setClient(ClientDTO client) {
		this.client = client;
	}
	public OrderDTO getPurchase_order() {
		return purchase_order;
	}
	public void setPurchase_order(OrderDTO purchase_order) {
		this.purchase_order = purchase_order;
	}
	public OperativeInfoDTO getOperative_info() {
		return operative_info;
	}
	public void setOperative_info(OperativeInfoDTO operative_info) {
		this.operative_info = operative_info;
	}
	public String get_available() {
		return _available;
	}
	public void set_available(String _available) {
		this._available = _available;
	}
	public String getCreated_on_utc() {
		return created_on_utc;
	}
	public void setCreated_on_utc(String created_on_utc) {
		this.created_on_utc = created_on_utc;
	}
}