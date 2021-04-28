package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DeliveryDetailReportDataDTO implements Serializable {
	
	private Long deliveryid;
	private Long deliverynumber;
	private Long orderid;
	private Long ocnumber;
	private Long locationid;
	private String locationcode;
	private String locationdesc;
	private Long departmentid;
	private String departmentcode;
	private String departmentdesc;
	private Long itemid;
	private String itemsku;
	private String vendoritemcode;
	private Long flowtypeid;
	private String flowtype;
	private Integer innerpack;
	private String itemdesc;
	private Double pendingunits;
	private Double availableunits;
	private Double receivedunits;
	private Long curve;
	private Long atcid;
	private String atccode;
	private Long originalvendorid;
	private String originalvendorcode;
	private String originalvendorname;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getLocationdesc() {
		return locationdesc;
	}
	public void setLocationdesc(String locationdesc) {
		this.locationdesc = locationdesc;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
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
	public Long getFlowtypeid() {
		return flowtypeid;
	}
	public void setFlowtypeid(Long flowtypeid) {
		this.flowtypeid = flowtypeid;
	}
	public String getFlowtype() {
		return flowtype;
	}
	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	public Integer getInnerpack() {
		return innerpack;
	}
	public void setInnerpack(Integer innerpack) {
		this.innerpack = innerpack;
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
	public Long getCurve() {
		return curve;
	}
	public void setCurve(Long curve) {
		this.curve = curve;
	}
	public Long getAtcid() {
		return atcid;
	}
	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}
	public String getAtccode() {
		return atccode;
	}
	public void setAtccode(String atccode) {
		this.atccode = atccode;
	}
	public Long getOriginalvendorid() {
		return originalvendorid;
	}
	public void setOriginalvendorid(Long originalvendorid) {
		this.originalvendorid = originalvendorid;
	}
	public String getOriginalvendorcode() {
		return originalvendorcode;
	}
	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}
	public String getOriginalvendorname() {
		return originalvendorname;
	}
	public void setOriginalvendorname(String originalvendorname) {
		this.originalvendorname = originalvendorname;
	}
		
}
