package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class UpdateUnitsOfDODeliveryDetailsInitParamDTO implements Serializable {

	private String vendorcode;
	private Long dodeliveryid;
	private ItemUnitsDTO[] details;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}
	public ItemUnitsDTO[] getDetails() {
		return details;
	}
	public void setDetails(ItemUnitsDTO[] details) {
		this.details = details;
	}		
}
