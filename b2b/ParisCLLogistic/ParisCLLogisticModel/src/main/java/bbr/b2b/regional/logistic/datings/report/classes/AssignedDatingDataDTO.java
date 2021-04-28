package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class AssignedDatingDataDTO implements Serializable {

	private Long datingid;
	private Long moduleid;
	private Long dockid;
	private String vendorcode;
	private Boolean domesticvendor;
	private String provider;
	private Long deliveryid;
	private String datingnumber;
	private String containernumber;
	private String currentdeliverystate;
	private String currentdeliverystatecode;
	private String deliverytype;
	private String deliveryflowtype;
	private Integer totalboxs;
	private Integer totalpallets;
	private Double availableunits;

	public Long getDatingid() {
		return datingid;
	}

	public void setDatingid(Long datingid) {
		this.datingid = datingid;
	}

	public Long getModuleid() {
		return moduleid;
	}

	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}

	public Long getDockid() {
		return dockid;
	}

	public void setDockid(Long dockid) {
		this.dockid = dockid;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public Boolean getDomesticvendor() {
		return domesticvendor;
	}

	public void setDomesticvendor(Boolean domesticvendor) {
		this.domesticvendor = domesticvendor;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Long getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}

	public String getDatingnumber() {
		return datingnumber;
	}

	public void setDatingnumber(String datingnumber) {
		this.datingnumber = datingnumber;
	}

	public String getContainernumber() {
		return containernumber;
	}

	public void setContainernumber(String containernumber) {
		this.containernumber = containernumber;
	}

	public String getCurrentdeliverystate() {
		return currentdeliverystate;
	}

	public void setCurrentdeliverystate(String currentdeliverystate) {
		this.currentdeliverystate = currentdeliverystate;
	}

	public String getCurrentdeliverystatecode() {
		return currentdeliverystatecode;
	}

	public void setCurrentdeliverystatecode(String currentdeliverystatecode) {
		this.currentdeliverystatecode = currentdeliverystatecode;
	}

	public String getDeliverytype() {
		return deliverytype;
	}

	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	public String getDeliveryflowtype() {
		return deliveryflowtype;
	}

	public void setDeliveryflowtype(String deliveryflowtype) {
		this.deliveryflowtype = deliveryflowtype;
	}

	public Integer getTotalboxs() {
		return totalboxs;
	}

	public void setTotalboxs(Integer totalboxs) {
		this.totalboxs = totalboxs;
	}

	public Integer getTotalpallets() {
		return totalpallets;
	}

	public void setTotalpallets(Integer totalpallets) {
		this.totalpallets = totalpallets;
	}

	public Double getAvailableunits() {
		return availableunits;
	}

	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}
}
