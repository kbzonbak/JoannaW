package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AddDatingAndDetailsInitParamDTO implements Serializable{
	
	private String locationcode;
	private String vendorcode;
	private String comment;
	private LocalDateTime when;
	private Long deliveryid;
	private ReserveDetailDTO[] details;
	
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public LocalDateTime getWhen() {
		return when;
	}
	public void setWhen(LocalDateTime when) {
		this.when = when;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public ReserveDetailDTO[] getDetails() {
		return details;
	}
	public void setDetails(ReserveDetailDTO[] details) {
		this.details = details;
	}
}
