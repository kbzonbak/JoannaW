package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class UploadDeliveryAvailabilityInitParamDTO implements Serializable {

	private Long deliveryId;
	private String vendorcode;
	private String fileName;
	
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
