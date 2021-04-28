package bbr.b2b.extractors.ripley.dto;

import java.util.List;

public class SupplyOrderResponseDTO {
	private String status_code;
	private String total_found;
	private String limit;
	private List<SupplyPurchaseOrderDTO> purchase_order_list;
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public List<SupplyPurchaseOrderDTO> getPurchase_order_list() {
		return purchase_order_list;
	}
	public void setPurchase_order_list(List<SupplyPurchaseOrderDTO> purchase_order_list) {
		this.purchase_order_list = purchase_order_list;
	}
	public String getTotal_found() {
		return total_found;
	}
	public void setTotal_found(String total_found) {
		this.total_found = total_found;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
}
