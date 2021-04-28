package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class DeleteAllResourceBlockingGroupInitParamDTO implements Serializable {

	private Long resourceblockinggroupid;
	private String locationcode;

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

}
