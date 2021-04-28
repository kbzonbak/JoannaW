package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AddDatingAndDetailsInitParamDTO implements Serializable {

	private Long dvrdeliveryid;
	private LocalDateTime requesteddate;
	private String locationcode;
	private String vendorcode;
	private String comment;
	private ReserveDetailDTO[] reservedetail;

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public LocalDateTime getRequesteddate() {
		return requesteddate;
	}

	public void setRequesteddate(LocalDateTime requesteddate) {
		this.requesteddate = requesteddate;
	}

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

	public ReserveDetailDTO[] getReservedetail() {
		return reservedetail;
	}

	public void setReservedetail(ReserveDetailDTO[] reservedetail) {
		this.reservedetail = reservedetail;
	}

}
