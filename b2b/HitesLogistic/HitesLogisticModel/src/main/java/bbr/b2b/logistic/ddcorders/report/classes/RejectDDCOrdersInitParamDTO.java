package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;

import bbr.b2b.logistic.dvrorders.report.classes.RejectDDCOrdersDataDTO;

public class RejectDDCOrdersInitParamDTO implements Serializable {

	private String filename;
	private String vendorcode;
	private RejectDDCOrdersDataDTO[] rejectddcordersdata;

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

	public RejectDDCOrdersDataDTO[] getRejectddcordersdata() {
		return rejectddcordersdata;
	}

	public void setRejectddcordersdata(RejectDDCOrdersDataDTO[] rejectddcordersdata) {
		this.rejectddcordersdata = rejectddcordersdata;
	}

}
