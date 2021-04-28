package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DvrDeliveryIdInitParamDTO implements Serializable {

	private String vendorcode;
	private Long deliveryid;

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public Long getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}

}
