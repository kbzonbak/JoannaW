package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class ProposedPackingListDataDTO implements Serializable {
	
	private Long orderid;
	private Long locationid;
	private String palletid;
	private Long atcid;
	private Long itemid;
	private Long ordernumber;
	private Long deliverynumber;
	private String taxnumber;
	private String destinationcode;	
	private String destinationdesc;
	private String departmentcode;
	private String departmentdesc;
	private String bulkid;
	private String atccode;
	private String itemsku;
	private String vendoritemcode;
	private String itemdesc;
	private String flowtype;
	private Double units;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
	public String getPalletid() {
		return palletid;
	}
	public void setPalletid(String palletid) {
		this.palletid = palletid;
	}
	public Long getAtcid() {
		return atcid;
	}
	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Long getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
	public String getTaxnumber() {
		return taxnumber;
	}
	public void setTaxnumber(String taxnumber) {
		this.taxnumber = taxnumber;
	}
	public String getDestinationcode() {
		return destinationcode;
	}
	public void setDestinationcode(String destinationcode) {
		this.destinationcode = destinationcode;
	}
	public String getDestinationdesc() {
		return destinationdesc;
	}
	public void setDestinationdesc(String destinationdesc) {
		this.destinationdesc = destinationdesc;
	}
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public String getDepartmentdesc() {
		return departmentdesc;
	}
	public void setDepartmentdesc(String departmentdesc) {
		this.departmentdesc = departmentdesc;
	}
	public String getBulkid() {
		return bulkid;
	}
	public void setBulkid(String bulkid) {
		this.bulkid = bulkid;
	}
	public String getAtccode() {
		return atccode;
	}
	public void setAtccode(String atccode) {
		this.atccode = atccode;
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
	public String getFlowtype() {
		return flowtype;
	}
	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}
	
}
