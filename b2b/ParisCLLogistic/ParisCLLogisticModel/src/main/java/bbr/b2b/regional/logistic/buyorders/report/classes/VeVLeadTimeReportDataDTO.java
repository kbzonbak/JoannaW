package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVLeadTimeReportDataDTO implements Serializable{

	private String itemsku;
	private String vendorcode;
	private String itemdescription;
	private Integer leadtime;
	
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getItemdescription() {
		return itemdescription;
	}
	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}
	public Integer getLeadtime() {
		return leadtime;
	}
	public void setLeadtime(Integer leadtime) {
		this.leadtime = leadtime;
	}	
}
