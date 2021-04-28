package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;

public class RescheduleDateDDCOrdersInitParamDTO implements Serializable {

	private String filename;
	private String vendorcode;
	private RescheduleDateDDCOrdersDataDTO[] rescheduledateddcordersdata;

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

	public RescheduleDateDDCOrdersDataDTO[] getRescheduledateddcordersdata() {
		return rescheduledateddcordersdata;
	}

	public void setRescheduledateddcordersdata(RescheduleDateDDCOrdersDataDTO[] rescheduledateddcordersdata) {
		this.rescheduledateddcordersdata = rescheduledateddcordersdata;
	}

}
