package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DvrOrderDetailExcelReportDataDTO implements Serializable {

	private Long number;
	private String vendordni;
	private String vendortradename;
	private String deliverylocationcode;
	private String deliverylocationname;
	private String destinationlocationcode;
	private String destinationlocationname;
	private String detailbarcode1;
	private String itemsku;
	private String vendoritemcode;
	private String style;
	private LocalDateTime emitted;
	private LocalDateTime deliverydate;
	private LocalDateTime expiration;
	private String season;
	private String department;
	private String itemname;
	private String size;
	private String color;
	private Double totalunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	private Double finalcost;
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getVendordni() {
		return vendordni;
	}
	public void setVendordni(String vendordni) {
		this.vendordni = vendordni;
	}
	public String getVendortradename() {
		return vendortradename;
	}
	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationname() {
		return deliverylocationname;
	}
	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}
	public String getDestinationlocationcode() {
		return destinationlocationcode;
	}
	public void setDestinationlocationcode(String destinationlocationcode) {
		this.destinationlocationcode = destinationlocationcode;
	}
	public String getDestinationlocationname() {
		return destinationlocationname;
	}
	public void setDestinationlocationname(String destinationlocationname) {
		this.destinationlocationname = destinationlocationname;
	}
	public String getDetailbarcode1() {
		return detailbarcode1;
	}
	public void setDetailbarcode1(String detailbarcode1) {
		this.detailbarcode1 = detailbarcode1;
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
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public LocalDateTime getEmitted() {
		return emitted;
	}
	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}
	public LocalDateTime getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(LocalDateTime deliverydate) {
		this.deliverydate = deliverydate;
	}
	public LocalDateTime getExpiration() {
		return expiration;
	}
	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
}