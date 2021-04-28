package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ImportedDatingRequestReportDataDTO implements Serializable {

	private Long datingrequestid;
	private Long deliveryid;  
	private Long datingnumber;  
	private String vendorname;  
	private Long taxdocumentnumber;
	private String container;
	private String importednumber;	
	private String deliverytype;
	private String flowtype;
	private LocalDateTime proposeddate;  
	private LocalDateTime requestdate;  	
	private Integer estimatedboxes;	
	private Integer pallets;
	private String comment;
	private String vendorcode;  
	private Double needunits;
	private Integer scheduling;
	private Long estimatedtime;
	
	public Long getDatingrequestid() {
		return datingrequestid;
	}
	public void setDatingrequestid(Long datingrequestid) {
		this.datingrequestid = datingrequestid;
	}
	public Long getEstimatedtime() {
		return estimatedtime;
	}
	public void setEstimatedtime(Long estimatedtime) {
		this.estimatedtime = estimatedtime;
	}
	public Integer getScheduling() {
		return scheduling;
	}
	public void setScheduling(Integer scheduling) {
		this.scheduling = scheduling;
	}
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
	public Long getTaxdocumentnumber() {
		return taxdocumentnumber;
	}
	public void setTaxdocumentnumber(Long taxdocumentnumber) {
		this.taxdocumentnumber = taxdocumentnumber;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public String getImportednumber() {
		return importednumber;
	}
	public void setImportednumber(String importednumber) {
		this.importednumber = importednumber;
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
	public Integer getEstimatedboxes() {
		return estimatedboxes;
	}
	public void setEstimatedboxes(Integer estimatedboxes) {
		this.estimatedboxes = estimatedboxes;
	}	
	public Integer getPallets() {
		return pallets;
	}
	public void setPallets(Integer pallets) {
		this.pallets = pallets;
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
