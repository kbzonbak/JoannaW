package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class RescheduleDateDVHOrdersInitParamDTO implements Serializable {

	private String filename;
	private String vendorcode;
	private RescheduleDateDVHOrdersDataDTO[] rescheduledatedvhordersdata;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public RescheduleDateDVHOrdersDataDTO[] getRescheduledatedvhordersdata() {
		return rescheduledatedvhordersdata;
	}

	public void setRescheduledatedvhordersdata(RescheduleDateDVHOrdersDataDTO[] rescheduledatedvhordersdata) {
		this.rescheduledatedvhordersdata = rescheduledatedvhordersdata;
	}

}
