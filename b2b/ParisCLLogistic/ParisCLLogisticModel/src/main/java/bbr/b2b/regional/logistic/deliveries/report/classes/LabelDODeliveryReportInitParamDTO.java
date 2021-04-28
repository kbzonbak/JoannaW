package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class LabelDODeliveryReportInitParamDTO implements Serializable {

	private Long deliveryid;
	private String vendorcode;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}		
}
