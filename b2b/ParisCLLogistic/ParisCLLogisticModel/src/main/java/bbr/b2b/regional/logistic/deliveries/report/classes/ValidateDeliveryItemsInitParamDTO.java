package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class ValidateDeliveryItemsInitParamDTO implements Serializable {

	private String vendorcode;
	private Long[] orderids;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}	
	public Long[] getOrderids() {
		return orderids;
	}
	public void setOrderids(Long[] orderids) {
		this.orderids = orderids;
	}

}
