package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ReserveDetailByDateAndLocationInitParamDTO implements Serializable {

	private Long deliveryId;
	private String date;
	private String vendorcode;
	private String locationcode;

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
