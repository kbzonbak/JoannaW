package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.data.interfaces.IBuyerVendor;

public class BuyerVendor implements IBuyerVendor {
	private BuyerVendorId id;

	public BuyerVendorId getId() {
		return this.id;
	}

	public void setId(BuyerVendorId id) {
		this.id = id;
	}
}
