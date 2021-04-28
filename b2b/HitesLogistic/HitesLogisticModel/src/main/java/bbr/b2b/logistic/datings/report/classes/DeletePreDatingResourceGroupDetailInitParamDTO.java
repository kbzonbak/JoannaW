package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class DeletePreDatingResourceGroupDetailInitParamDTO implements Serializable {

	private String vendorcode;
	private String locationcode;
	private ReserveDetailDTO[] reservedetails;

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

	public ReserveDetailDTO[] getReservedetails() {
		return reservedetails;
	}

	public void setReservedetails(ReserveDetailDTO[] reservedetails) {
		this.reservedetails = reservedetails;
	}

}
