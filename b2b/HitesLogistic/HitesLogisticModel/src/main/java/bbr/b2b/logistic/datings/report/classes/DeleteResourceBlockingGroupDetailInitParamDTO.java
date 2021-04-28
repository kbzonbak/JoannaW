package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class DeleteResourceBlockingGroupDetailInitParamDTO implements Serializable {

	private Long resourceblockinggroupid;
	private String locationcode;
	private ReserveDetailDTO[] reservedetails;

	public Long getResourceblockinggroupid() {
		return resourceblockinggroupid;
	}

	public void setResourceblockinggroupid(Long resourceblockinggroupid) {
		this.resourceblockinggroupid = resourceblockinggroupid;
	}

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public ReserveDetailDTO[] getReservedetails() {
		return reservedetails;
	}

	public void setReservedetails(ReserveDetailDTO[] reservedetails) {
		this.reservedetails = reservedetails;
	}

}
