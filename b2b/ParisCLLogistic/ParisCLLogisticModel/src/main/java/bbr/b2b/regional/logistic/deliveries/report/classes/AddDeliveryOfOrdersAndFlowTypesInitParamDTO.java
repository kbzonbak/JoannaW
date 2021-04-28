package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class AddDeliveryOfOrdersAndFlowTypesInitParamDTO implements Serializable {

	private Long[] orderids;
	private Long[] flowtypeids;
	private String vendorcode;
	private Boolean stock;
	private Boolean store;
	
	public Long[] getOrderids() {
		return orderids;
	}
	public void setOrderids(Long[] orderids) {
		this.orderids = orderids;
	}
	public Long[] getFlowtypeids() {
		return flowtypeids;
	}
	public void setFlowtypeids(Long[] flowtypeids) {
		this.flowtypeids = flowtypeids;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Boolean getStock() {
		return stock;
	}
	public void setStock(Boolean stock) {
		this.stock = stock;
	}
	public Boolean getStore() {
		return store;
	}
	public void setStore(Boolean store) {
		this.store = store;
	}	
}
