package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class DODeliveryDetailMobileDataDTO implements Serializable {

	private Long deliveryid;
	private String ordernumberrequest;
	private String vendorname;
	private String commiteddate; //commiteddate en despacho
	private String clientname;
	private String clientrut;
	private String clientphone;
	private String clientcommune;
	private String clientcity;
	private String clientaddress;
	
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
	public String getCommiteddate() {
		return commiteddate;
	}
	public void setCommiteddate(String commiteddate) {
		this.commiteddate = commiteddate;
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
	
}
