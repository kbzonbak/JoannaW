package bbr.b2b.extractors.ripley.dto;

import java.util.List;

public class SupplyPurchaseOrderDTO {

	private String _id;
	private SupplyOrderDTO purchase_order;
	private boolean _available;
	private String _vendor_id;
	private String created_on_utc;
	private List<SupplyDetailDTO> detail;
	private OperativeInfoDTO operative_info;
	private SupplyVendor vendor;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public SupplyOrderDTO getPurchase_order() {
		return purchase_order;
	}
	public void setPurchase_order(SupplyOrderDTO purchase_order) {
		this.purchase_order = purchase_order;
	}
	public boolean is_available() {
		return _available;
	}
	public void set_available(boolean _available) {
		this._available = _available;
	}
	public String get_vendor_id() {
		return _vendor_id;
	}
	public void set_vendor_id(String _vendor_id) {
		this._vendor_id = _vendor_id;
	}
	public String getCreated_on_utc() {
		return created_on_utc;
	}
	public void setCreated_on_utc(String created_on_utc) {
		this.created_on_utc = created_on_utc;
	}
	public List<SupplyDetailDTO> getDetails() {
		return detail;
	}
	public void setDetails(List<SupplyDetailDTO> detail) {
		this.detail = detail;
	}
	public OperativeInfoDTO getOperative_info() {
		return operative_info;
	}
	public void setOperative_info(OperativeInfoDTO operative_info) {
		this.operative_info = operative_info;
	}
	public SupplyVendor getVendor() {
		return vendor;
	}
	public void setVendor(SupplyVendor vendor) {
		this.vendor = vendor;
	}
}
