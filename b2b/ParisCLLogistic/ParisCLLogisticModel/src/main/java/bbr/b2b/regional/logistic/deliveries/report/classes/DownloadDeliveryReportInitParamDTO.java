package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;


public class DownloadDeliveryReportInitParamDTO implements Serializable {

	private Long[] deliveryids;
	private String vendorcode;
	
	public Long[] getDeliveryids() {
		return deliveryids;
	}

	public void setDeliveryids(Long[] deliveryids) {
		this.deliveryids = deliveryids;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}	
}
