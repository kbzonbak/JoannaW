package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class DatingRequestRejectInitParamDTO implements Serializable {

	private String locationcode;
	private Long dvrdeliveryid;
	private String reason;

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
