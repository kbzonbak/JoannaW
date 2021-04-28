package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class ExcelDeliveryDetailReportInitParamDTO implements Serializable {

	private Long deliveryid;
	private String vendorcode;
	private String locationcode;

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

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

}
