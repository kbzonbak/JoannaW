package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ExcelDatingReportDataDTO implements Serializable{

	private Long datingnumber;
	private Long orderid;
	private Long deliveryid;
	private String requesteddate;
	private String starts;
	private String ends;
	private String dockcode;
	private String vendorrut;
	private String vendorname;
	private String sectionname;
	private String sectioncode;
	private Long deliverynumber;
	private String deliverystatetype;
	private Long ordernumber;
	private String ordertype;
	private String asigneddate;
	private String locationname;
	private String locationcode;
	private Double availableunits;
	private Double pendingunits;
	private Double receivedunits;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public String getRequesteddate() {
		return requesteddate;
	}
	public void setRequesteddate(String requesteddate) {
		this.requesteddate = requesteddate;
	}
	public String getStarts() {
		return starts;
	}
	public void setStarts(String starts) {
		this.starts = starts;
	}
	public String getEnds() {
		return ends;
	}
	public void setEnds(String ends) {
		this.ends = ends;
	}
	public String getDockcode() {
		return dockcode;
	}
	public void setDockcode(String dockcode) {
		this.dockcode = dockcode;
	}
	public String getVendorrut() {
		return vendorrut;
	}
	public void setVendorrut(String vendorrut) {
		this.vendorrut = vendorrut;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
	public String getDeliverystatetype() {
		return deliverystatetype;
	}
	public void setDeliverystatetype(String deliverystatetype) {
		this.deliverystatetype = deliverystatetype;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getAsigneddate() {
		return asigneddate;
	}
	public void setAsigneddate(String asigneddate) {
		this.asigneddate = asigneddate;
	}
	public Double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public String getSectionname() {
		return sectionname;
	}
	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}
	public String getSectioncode() {
		return sectioncode;
	}
	public void setSectioncode(String sectioncode) {
		this.sectioncode = sectioncode;
	}
	public String getLocationname() {
		return locationname;
	}
	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public Long getDatingnumber() {
		return datingnumber;
	}
	public void setDatingnumber(Long datingnumber) {
		this.datingnumber = datingnumber;
	}	
}
	    