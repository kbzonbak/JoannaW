package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class DODeliveryMobileDataDTO implements Serializable {

	private Long deliveryid;
	private String ordernumberrequest;
	private Long ordernumber;
	private String vendorname;
	private String committeddate; //commiteddate en despacho
	private String clientname;
	private String clientrut;
	private String clientphone;
	private String clientcommune;
	private String clientcity;
	private String clientaddress;
	private String currentstate;
	private String retailercode;
	private String retailername;

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
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getCommitteddate() {
		return committeddate;
	}
	public void setCommitteddate(String committeddate) {
		this.committeddate = committeddate;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getClientrut() {
		return clientrut;
	}
	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
	}
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	public String getClientcommune() {
		return clientcommune;
	}
	public void setClientcommune(String clientcommune) {
		this.clientcommune = clientcommune;
	}
	public String getClientcity() {
		return clientcity;
	}
	public void setClientcity(String clientcity) {
		this.clientcity = clientcity;
	}
	public String getClientaddress() {
		return clientaddress;
	}
	public void setClientaddress(String clientaddress) {
		this.clientaddress = clientaddress;
	}
	public String getCurrentstate() {
		return currentstate;
	}
	public void setCurrentstate(String currentstate) {
		this.currentstate = currentstate;
	}

}
