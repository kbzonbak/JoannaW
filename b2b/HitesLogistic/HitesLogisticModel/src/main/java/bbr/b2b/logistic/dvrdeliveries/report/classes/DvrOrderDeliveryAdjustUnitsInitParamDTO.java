package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DvrOrderDeliveryAdjustUnitsInitParamDTO implements Serializable {

	private String filename;
	private String vendorcode;
	private Long dvrdeliveryid;
	private DvrOrderDeliveryAdjustUnitsDetailDTO[] details;

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

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public DvrOrderDeliveryAdjustUnitsDetailDTO[] getDetails() {
		return details;
	}

	public void setDetails(DvrOrderDeliveryAdjustUnitsDetailDTO[] details) {
		this.details = details;
	}

}
