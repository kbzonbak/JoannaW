package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class ReceptionDetailDTO implements Serializable {
	
	private Long itemid;
	private String iteminternalcode;
	private String itemname;
	private String vendoritemcode;
	private Double receivedunits;
	private Double finalcost;
	private Double totalreceived;
	
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
	public Double getTotalreceived() {
		return totalreceived;
	}
	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}
}
