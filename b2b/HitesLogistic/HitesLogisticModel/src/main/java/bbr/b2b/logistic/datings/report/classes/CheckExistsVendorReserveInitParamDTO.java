package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckExistsVendorReserveInitParamDTO implements Serializable {

	private String vendorcode;
	private String locationcode;
	private LocalDateTime reservedate;

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

	public LocalDateTime getReservedate() {
		return reservedate;
	}

	public void setReservedate(LocalDateTime reservedate) {
		this.reservedate = reservedate;
	}

}