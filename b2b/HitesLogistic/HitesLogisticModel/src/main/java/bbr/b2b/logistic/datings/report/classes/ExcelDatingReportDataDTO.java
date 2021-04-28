package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class ExcelDatingReportDataDTO implements Serializable {

	private Long reserveid;
	private String vendorcode;
	private String vendorname;
	private Long ocnumber;
	private String ordertype;
	private String departments;
	private LocalDateTime deliverydate;
	private Long dvrdeliverynumber;
	private String dvrdeliverystatetype;
	private String documentnumber;
	private String documenttype;
	private LocalDateTime datingrequestwhen;
	private LocalDateTime datingrequestdate;
	private String timemodule;
	private Integer estimatedbulks;
	private LocalDateTime datingconfirmationdate;
	private Double confirmedunits; // availableunits
	private Double totallpn;
	private LocalDateTime datingdate;
	private LocalDateTime datinginitialtime;
	private Long datingdurationsql;
	private Duration datingduration;
	private String dockname;
	
	public Long getReserveid() {
		return reserveid;
	}
	public void setReserveid(Long reserveid) {
		this.reserveid = reserveid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getDepartments() {
		return departments;
	}
	public void setDepartments(String departments) {
		this.departments = departments;
	}
	public LocalDateTime getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(LocalDateTime deliverydate) {
		this.deliverydate = deliverydate;
	}
	public Long getDvrdeliverynumber() {
		return dvrdeliverynumber;
	}
	public void setDvrdeliverynumber(Long dvrdeliverynumber) {
		this.dvrdeliverynumber = dvrdeliverynumber;
	}
	public String getDvrdeliverystatetype() {
		return dvrdeliverystatetype;
	}
	public void setDvrdeliverystatetype(String dvrdeliverystatetype) {
		this.dvrdeliverystatetype = dvrdeliverystatetype;
	}
	public String getDocumentnumber() {
		return documentnumber;
	}
	public void setDocumentnumber(String documentnumber) {
		this.documentnumber = documentnumber;
	}
	public String getDocumenttype() {
		return documenttype;
	}
	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}
	public LocalDateTime getDatingrequestwhen() {
		return datingrequestwhen;
	}
	public void setDatingrequestwhen(LocalDateTime datingrequestwhen) {
		this.datingrequestwhen = datingrequestwhen;
	}
	public LocalDateTime getDatingrequestdate() {
		return datingrequestdate;
	}
	public void setDatingrequestdate(LocalDateTime datingrequestdate) {
		this.datingrequestdate = datingrequestdate;
	}
	public String getTimemodule() {
		return timemodule;
	}
	public void setTimemodule(String timemodule) {
		this.timemodule = timemodule;
	}
	public Integer getEstimatedbulks() {
		return estimatedbulks;
	}
	public void setEstimatedbulks(Integer estimatedbulks) {
		this.estimatedbulks = estimatedbulks;
	}
	public LocalDateTime getDatingconfirmationdate() {
		return datingconfirmationdate;
	}
	public void setDatingconfirmationdate(LocalDateTime datingconfirmationdate) {
		this.datingconfirmationdate = datingconfirmationdate;
	}
	public Double getConfirmedunits() {
		return confirmedunits;
	}
	public void setConfirmedunits(Double confirmedunits) {
		this.confirmedunits = confirmedunits;
	}
	public Double getTotallpn() {
		return totallpn;
	}
	public void setTotallpn(Double totallpn) {
		this.totallpn = totallpn;
	}
	public LocalDateTime getDatingdate() {
		return datingdate;
	}
	public void setDatingdate(LocalDateTime datingdate) {
		this.datingdate = datingdate;
	}
	public LocalDateTime getDatinginitialtime() {
		return datinginitialtime;
	}
	public void setDatinginitialtime(LocalDateTime datinginitialtime) {
		this.datinginitialtime = datinginitialtime;
	}
	public Long getDatingdurationsql() {
		return datingdurationsql;
	}
	public void setDatingdurationsql(Long datingdurationsql) {
		this.datingdurationsql = datingdurationsql;
		// Setea también datingduration
		if(datingdurationsql != null) {
			this.datingduration = Duration.ofSeconds(datingdurationsql);
		}
	}
	public Duration getDatingduration() {
		return datingduration;
	}
	public void setDatingduration(Duration datingduration) {
		this.datingduration = datingduration;
	}
	public String getDockname() {
		return dockname;
	}
	public void setDockname(String dockname) {
		this.dockname = dockname;
	}
}
