package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DatingRequestReportDataDTO implements Serializable {

	private Long dvrdeliveryid;
	private Long deliverynumber;
	private String vendorcode;
	private String vendorname;
	private LocalDateTime datingrequestwhen; // Fecha en que se hizo la solicitud
	private LocalDateTime requestdate; // Fecha que se ingresó en la solicitud (Fecha deseada)
	private String datingrequestmodule; // Modulo horario elegido en la solicitud
	private Integer estimatedtime;
	private Integer bulks;
	private Integer trucks;
	private String ordertype;
	private Double availableunits; // cantidad comprometida
	private String datingrequestcomment;

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public Long getDeliverynumber() {
		return deliverynumber;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public String getVendorname() {
		return vendorname;
	}

	public LocalDateTime getDatingrequestwhen() {
		return datingrequestwhen;
	}

	public LocalDateTime getRequestdate() {
		return requestdate;
	}

	public String getDatingrequestmodule() {
		return datingrequestmodule;
	}

	public Integer getEstimatedtime() {
		return estimatedtime;
	}

	public Integer getBulks() {
		return bulks;
	}

	public Integer getTrucks() {
		return trucks;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public Double getAvailableunits() {
		return availableunits;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public void setDatingrequestwhen(LocalDateTime datingrequestwhen) {
		this.datingrequestwhen = datingrequestwhen;
	}

	public void setRequestdate(LocalDateTime requestdate) {
		this.requestdate = requestdate;
	}

	public void setDatingrequestmodule(String datingrequestmodule) {
		this.datingrequestmodule = datingrequestmodule;
	}

	public void setEstimatedtime(Integer estimatedtime) {
		this.estimatedtime = estimatedtime;
	}

	public void setBulks(Integer bulks) {
		this.bulks = bulks;
	}

	public void setTrucks(Integer trucks) {
		this.trucks = trucks;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}

	public String getDatingrequestcomment() {
		return datingrequestcomment;
	}

	public void setDatingrequestcomment(String datingrequestcomment) {
		this.datingrequestcomment = datingrequestcomment;
	}

}
