package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DocumentUpdateInitParamDTO implements Serializable {

	private Long dvrdeliveryid;
	private String vendorcode;
	private DocumentUpdateDTO[] update;
	
	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}
	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public DocumentUpdateDTO[] getUpdate() {
		return update;
	}
	public void setUpdate(DocumentUpdateDTO[] update) {
		this.update = update;
	}
}
