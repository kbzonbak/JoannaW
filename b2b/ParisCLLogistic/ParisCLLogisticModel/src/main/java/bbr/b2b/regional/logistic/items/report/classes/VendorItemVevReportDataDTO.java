package bbr.b2b.regional.logistic.items.report.classes;

import java.io.Serializable;

public class VendorItemVevReportDataDTO implements Serializable{

	private String itemsku;
	private String description;	
	private String vendoritemcode;
	private Boolean vev;
	
	
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public Boolean getVev() {
		return vev;
	}
	public void setVev(Boolean vev) {
		this.vev = vev;
	}	
	
}
