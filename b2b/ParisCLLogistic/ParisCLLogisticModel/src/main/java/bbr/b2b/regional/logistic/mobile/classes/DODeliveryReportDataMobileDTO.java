package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class DODeliveryReportDataMobileDTO implements Serializable {

	private Long deliveryid;
	private String ordernumberrequest;
	private String vendorname;
	private String clientname;
	private String clientaddress;
	private String clientcommune;
	private String searchfield;
	private String retailercode;
	private String retailername;

	public Long getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}

	public String getOrdernumberrequest() {
		return ordernumberrequest;
	}

	public void setOrdernumberrequest(String ordernumberrequest) {
		this.ordernumberrequest = ordernumberrequest;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getClientaddress() {
		return clientaddress;
	}

	public void setClientaddress(String clientaddress) {
		this.clientaddress = clientaddress;
	}

	public String getClientcommune() {
		return clientcommune;
	}

	public void setClientcommune(String clientcommune) {
		this.clientcommune = clientcommune;
	}

	public String getSearchfield() {
		return searchfield;
	}

	public void setSearchfield(String searchfield) {
		this.searchfield = searchfield;
	}

	public String getRetailercode() {
		return retailercode;
	}

	public void setRetailercode(String retailercode) {
		this.retailercode = retailercode;
	}

	public String getRetailername() {
		return retailername;
	}

	public void setRetailername(String retailername) {
		this.retailername = retailername;
	}

}
