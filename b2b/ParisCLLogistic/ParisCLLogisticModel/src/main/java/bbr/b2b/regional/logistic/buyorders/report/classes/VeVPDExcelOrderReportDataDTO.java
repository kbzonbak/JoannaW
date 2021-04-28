package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;


public class VeVPDExcelOrderReportDataDTO implements Serializable {
	
	private Long docnumber;
	private String requestnumber;
	private String departmentcode;
	private String departmentdesc;
	private String orderstatedesc;
	private String ordertypedesc;
	private LocalDateTime emitteddate;
	private LocalDateTime validdate;
	private LocalDateTime reprogdate;
	private String clientrut;
	private String clientname;
	private String clientphone;
	private String clientaddress;
	private String clientcommune;
	private String clientcity;
	private String clienthousenumber;
	private String clientdeptnumber;
	private String clientroadnumber;
	private String clientregion;
	private String occomment;
	private String itemsku;
	private String vendoritemcode;
	private String itemdesc;
	private Double totalunits;
	private Double todeliveryunits; 
	private Double receivedunits; 
	private Double pendingunits; 
	private Double listcost;
	private String deliverystatetype;
	private String salestorename;
	private String salestorecode;
	private String sendnumber;
	
	public Long getDocnumber() {
		return docnumber;
	}
	public void setDocnumber(Long docnumber) {
		this.docnumber = docnumber;
	}
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public String getDepartmentdesc() {
		return departmentdesc;
	}
	public void setDepartmentdesc(String departmentdesc) {
		this.departmentdesc = departmentdesc;
	}
	public String getOrderstatedesc() {
		return orderstatedesc;
	}
	public void setOrderstatedesc(String orderstatedesc) {
		this.orderstatedesc = orderstatedesc;
	}
	public String getOrdertypedesc() {
		return ordertypedesc;
	}
	public void setOrdertypedesc(String ordertypedesc) {
		this.ordertypedesc = ordertypedesc;
	}
	public LocalDateTime getEmitteddate() {
		return emitteddate;
	}
	public void setEmitteddate(LocalDateTime emitteddate) {
		this.emitteddate = emitteddate;
	}
	public LocalDateTime getValiddate() {
		return validdate;
	}
	public void setValiddate(LocalDateTime validdate) {
		this.validdate = validdate;
	}
	public LocalDateTime getReprogdate() {
		return reprogdate;
	}
	public void setReprogdate(LocalDateTime reprogdate) {
		this.reprogdate = reprogdate;
	}
	public String getClientrut() {
		return clientrut;
	}
	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
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
	public String getClienthousenumber() {
		return clienthousenumber;
	}
	public void setClienthousenumber(String clienthousenumber) {
		this.clienthousenumber = clienthousenumber;
	}
	public String getClientdeptnumber() {
		return clientdeptnumber;
	}
	public void setClientdeptnumber(String clientdeptnumber) {
		this.clientdeptnumber = clientdeptnumber;
	}
	public String getClientroadnumber() {
		return clientroadnumber;
	}
	public void setClientroadnumber(String clientroadnumber) {
		this.clientroadnumber = clientroadnumber;
	}
	public String getClientregion() {
		return clientregion;
	}
	public void setClientregion(String clientregion) {
		this.clientregion = clientregion;
	}
	public String getOccomment() {
		return occomment;
	}
	public void setOccomment(String occomment) {
		this.occomment = occomment;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public String getItemdesc() {
		return itemdesc;
	}
	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getListcost() {
		return listcost;
	}
	public void setListcost(Double listcost) {
		this.listcost = listcost;
	}
	public String getDeliverystatetype() {
		return deliverystatetype;
	}
	public void setDeliverystatetype(String deliverystatetype) {
		this.deliverystatetype = deliverystatetype;
	}
	public String getSalestorename() {
		return salestorename;
	}
	public void setSalestorename(String salestorename) {
		this.salestorename = salestorename;
	}
	public String getSalestorecode() {
		return salestorecode;
	}
	public void setSalestorecode(String salestorecode) {
		this.salestorecode = salestorecode;
	}
	public String getSendnumber() {
		return sendnumber;
	}
	public void setSendnumber(String sendnumber) {
		this.sendnumber = sendnumber;
	}	
}
