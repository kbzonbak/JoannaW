package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class RejectDVHOrdersInitParamDTO implements Serializable {

	private String filename;
	private String vendorcode;
	private RejectDVHOrdersDataDTO[] rejectdvhordersdata;

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

	public RejectDVHOrdersDataDTO[] getRejectdvhordersdata() {
		return rejectdvhordersdata;
	}

	public void setRejectdvhordersdata(RejectDVHOrdersDataDTO[] rejectdvhordersdata) {
		this.rejectdvhordersdata = rejectdvhordersdata;
	}

}
