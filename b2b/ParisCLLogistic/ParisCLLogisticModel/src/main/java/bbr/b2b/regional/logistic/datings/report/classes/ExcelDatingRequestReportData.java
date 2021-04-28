package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ExcelDatingRequestReportData implements Serializable {

	private Long id;

	private Long deliverynumber;

	private String vendortradename;

	private String departmentcode;

	private String departmentname;

	private String deliverytype;

	private String flowtype;

	private String request;

	private String requestdate;

	private Integer trucksnumber;

	private Integer estimatedboxes;

	private Integer estimatedpallets;

	private Long ordernumber;

	private String locationcode;

	private String locationname;

	private String internalcode;

	private String vendoritemcode;

	private String itemname;

	private Double availableunits;

	private String requestnumber;

	private String ordervalid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeliverynumber() {
		return deliverynumber;
	}

	public void setDeliverynumber(Long deliverynumber) {
		this.deliverynumber = deliverynumber;
	}

	public String getVendortradename() {
		return vendortradename;
	}

	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}

	public String getDepartmentcode() {
		return departmentcode;
	}

	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
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

	public Integer getTrucksnumber() {
		return trucksnumber;
	}

	public void setTrucksnumber(Integer trucksnumber) {
		this.trucksnumber = trucksnumber;
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

	public Long getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getInternalcode() {
		return internalcode;
	}

	public void setInternalcode(String internalcode) {
		this.internalcode = internalcode;
	}

	public String getVendoritemcode() {
		return vendoritemcode;
	}

	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public Double getAvailableunits() {
		return availableunits;
	}

	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}

	public String getRequestnumber() {
		return requestnumber;
	}

	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequestdate() {
		return requestdate;
	}

	public void setRequestdate(String requestdate) {
		this.requestdate = requestdate;
	}

	public String getOrdervalid() {
		return ordervalid;
	}

	public void setOrdervalid(String ordervalid) {
		this.ordervalid = ordervalid;
	}

}
