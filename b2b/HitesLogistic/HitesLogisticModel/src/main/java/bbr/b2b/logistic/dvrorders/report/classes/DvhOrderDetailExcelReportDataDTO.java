package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DvhOrderDetailExcelReportDataDTO implements Serializable {

	private Long number;
	private String referencenumber;
	private LocalDateTime emitted;
	private LocalDateTime expiration;
	private LocalDateTime deliverydate;
	private LocalDateTime clientdeliverydate;
	private String vendordni;
	private String vendortradename;
	private String deliverylocationcode;
	private String deliverylocationname;
	private String salelocationcode;
	private String salelocationname;
	private String departmentname;
	private String route;
	private String clientdni;
	private String clientname;
	private String clientaddress;
	private String clientcommune;
	private String clientcity;
	private String clientphone;
	private String clientemail;
	private String clientcomment;
	private String boxnumber;
	private String itemsku;
	private String itemname;
	private String itemshortcode;
	private String itembarcode1;
	private Double normalprice;
	private Double finalcost;
	private Double totalunits;
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getReferencenumber() {
		return referencenumber;
	}
	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}
	public LocalDateTime getEmitted() {
		return emitted;
	}
	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}
	public LocalDateTime getExpiration() {
		return expiration;
	}
	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}
	public LocalDateTime getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(LocalDateTime deliverydate) {
		this.deliverydate = deliverydate;
	}
	public LocalDateTime getClientdeliverydate() {
		return clientdeliverydate;
	}
	public void setClientdeliverydate(LocalDateTime clientdeliverydate) {
		this.clientdeliverydate = clientdeliverydate;
	}
	public String getVendordni() {
		return vendordni;
	}
	public void setVendordni(String vendordni) {
		this.vendordni = vendordni;
	}
	public String getVendortradename() {
		return vendortradename;
	}
	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationname() {
		return deliverylocationname;
	}
	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}
	public String getSalelocationcode() {
		return salelocationcode;
	}
	public void setSalelocationcode(String salelocationcode) {
		this.salelocationcode = salelocationcode;
	}
	public String getSalelocationname() {
		return salelocationname;
	}
	public void setSalelocationname(String salelocationname) {
		this.salelocationname = salelocationname;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getClientdni() {
		return clientdni;
	}
	public void setClientdni(String clientdni) {
		this.clientdni = clientdni;
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
	public String getClientcity() {
		return clientcity;
	}
	public void setClientcity(String clientcity) {
		this.clientcity = clientcity;
	}
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	public String getClientemail() {
		return clientemail;
	}
	public void setClientemail(String clientemail) {
		this.clientemail = clientemail;
	}
	public String getClientcomment() {
		return clientcomment;
	}
	public void setClientcomment(String clientcomment) {
		this.clientcomment = clientcomment;
	}
	public String getBoxnumber() {
		return boxnumber;
	}
	public void setBoxnumber(String boxnumber) {
		this.boxnumber = boxnumber;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getItemshortcode() {
		return itemshortcode;
	}
	public void setItemshortcode(String itemshortcode) {
		this.itemshortcode = itemshortcode;
	}
	public String getItembarcode1() {
		return itembarcode1;
	}
	public void setItembarcode1(String itembarcode1) {
		this.itembarcode1 = itembarcode1;
	}
	public Double getNormalprice() {
		return normalprice;
	}
	public void setNormalprice(Double normalprice) {
		this.normalprice = normalprice;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}		
}
