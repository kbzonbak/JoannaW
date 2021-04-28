package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class DeleteAllPreDatingResourceGroupInitParamDTO implements Serializable {

	private String vendorcode;
	private Long predatingresourcegroupid;
	private String locationcode;

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public Long getPredatingresourcegroupid() {
		return predatingresourcegroupid;
	}

	public void setPredatingresourcegroupid(Long predatingresourcegroupid) {
		this.predatingresourcegroupid = predatingresourcegroupid;
	}

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

}
