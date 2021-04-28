package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DatingRequestReportDataDTO implements Serializable {

	private Long deliveryid;
	private Long datingnumber;
	private String vendorname;
	private String departmentcode;
	private String department;
	private String deliverytypecode;
	private String deliverytype;
	private String flowtype;
	private LocalDateTime proposeddate;
	private LocalDateTime requestdate;
	private Integer trucks;
	private Integer estimatedboxes;
	private Integer estimatedpallets;
	private String comment;
	private String vendorcode;
	private Double needunits;

	public Long getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}

	public Long getDatingnumber() {
		return datingnumber;
	}

	public void setDatingnumber(Long datingnumber) {
		this.datingnumber = datingnumber;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getDepartmentcode() {
		return departmentcode;
	}

	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDeliverytypecode() {
		return deliverytypecode;
	}

	public void setDeliverytypecode(String deliverytypecode) {
		this.deliverytypecode = deliverytypecode;
	}

	public String getDeliverytype() {
		return deliverytype;
	}

	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	public String getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}

	public LocalDateTime getProposeddate() {
		return proposeddate;
	}

	public void setProposeddate(LocalDateTime proposeddate) {
		this.proposeddate = proposeddate;
	}

	public LocalDateTime getRequestdate() {
		return requestdate;
	}

	public void setRequestdate(LocalDateTime requestdate) {
		this.requestdate = requestdate;
	}

	public Integer getTrucks() {
		return trucks;
	}

	public void setTrucks(Integer trucks) {
		this.trucks = trucks;
	}

	public Integer getEstimatedboxes() {
		return estimatedboxes;
	}

	public void setEstimatedboxes(Integer estimatedboxes) {
		this.estimatedboxes = estimatedboxes;
	}

	public Integer getEstimatedpallets() {
		return estimatedpallets;
	}

	public void setEstimatedpallets(Integer estimatedpallets) {
		this.estimatedpallets = estimatedpallets;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public Double getNeedunits() {
		return needunits;
	}

	public void setNeedunits(Double needunits) {
		this.needunits = needunits;
	}

}
