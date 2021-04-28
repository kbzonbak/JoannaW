package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DvrDeliveryDatingDocumentReportDataDTO implements Serializable {

	private Long deliveryid;
	private Long deliverynumber;
	private String vendorcode;
	private String vendorname;
	private Long ordertypeid;
	private String ordertypecode;
	private String ordertypedescription;
	private String dockname;
	private LocalDateTime starttime;
	private LocalDateTime endtime;
	private String nextaction;
	private Integer documents;

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
	
	public Long getOrdertypeid() {
		return ordertypeid;
	}

	public void setOrdertypeid(Long ordertypeid) {
		this.ordertypeid = ordertypeid;
	}

	public String getOrdertypecode() {
		return ordertypecode;
	}

	public void setOrdertypecode(String ordertypecode) {
		this.ordertypecode = ordertypecode;
	}

	public String getOrdertypedescription() {
		return ordertypedescription;
	}

	public void setOrdertypedescription(String ordertypedescription) {
		this.ordertypedescription = ordertypedescription;
	}

	public String getDockname() {
		return dockname;
	}

	public void setDockname(String dockname) {
		this.dockname = dockname;
	}

	public LocalDateTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalDateTime starttime) {
		this.starttime = starttime;
	}

	public LocalDateTime getEndtime() {
		return endtime;
	}

	public void setEndtime(LocalDateTime endtime) {
		this.endtime = endtime;
	}

	public String getNextaction() {
		return nextaction;
	}

	public void setNextaction(String nextaction) {
		this.nextaction = nextaction;
	}

	public Integer getDocuments() {
		return documents;
	}

	public void setDocuments(Integer documents) {
		this.documents = documents;
	}
}
