package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DODeliveryDetailReportDataDTO implements Serializable {
	
	private Long deliveryid;  
	private Long itemid;
	private String itemsku;  
	private String vendoritemcode;  
	private String itemdesc;			
	private Double pendingunits;  
	private Double availableunits;  
	private Double receivedunits;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public String getItemdesc() {
		return itemdesc;
	}
	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}	
}
