package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;


public class DODeliveryReportDataDTO implements Serializable {
	
	private Long deliveryid;
	private Long deliverynumber;
	private Long ocnumber;
	private String requestnumber;
	private String clientcommune;
	private String clientname;
	private String clientrut;
	private String clientaddress;
	private String clientcity;
	private String clientphone;
	private String commiteddate;
	private String deliverystatetypecode;
	private String deliverystatetypedesc;
	private String currentstdate;
	private String occomment;
	private String dlcomment;	
	private String receptiontypecode;
	private String receptiontypedesc;
	private String stchangedate;
	private String dispatcheremail;
	private Long couriertag;
	private String sendnumber;
	private Long withdrawalnumber;
	private String withdrawaldate;
	private Long firstwithdrawalnumber;
	private String firstwithdrawaldate;
	private String penultimatestate;
	private String ultimatestate;
	private Long reschedulereasonid;
	private String reschedulereasoncode;
	private String reschedulereasondesc;
	private Boolean courierreceived;
	
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
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}	
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}
	public String getClientcommune() {
		return clientcommune;
	}
	public void setClientcommune(String clientcommune) {
		this.clientcommune = clientcommune;
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
	public String getClientaddress() {
		return clientaddress;
	}
	public void setClientaddress(String clientaddress) {
		this.clientaddress = clientaddress;
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
	public String getCommiteddate() {
		return commiteddate;
	}
	public void setCommiteddate(String commiteddate) {
		this.commiteddate = commiteddate;
	}
	public String getDeliverystatetypecode() {
		return deliverystatetypecode;
	}
	public void setDeliverystatetypecode(String deliverystatetypecode) {
		this.deliverystatetypecode = deliverystatetypecode;
	}
	public String getDeliverystatetypedesc() {
		return deliverystatetypedesc;
	}
	public void setDeliverystatetypedesc(String deliverystatetypedesc) {
		this.deliverystatetypedesc = deliverystatetypedesc;
	}
	public String getCurrentstdate() {
		return currentstdate;
	}
	public void setCurrentstdate(String currentstdate) {
		this.currentstdate = currentstdate;
	}	
	public String getOccomment() {
		return occomment;
	}
	public void setOccomment(String occomment) {
		this.occomment = occomment;
	}
	public String getDlcomment() {
		return dlcomment;
	}
	public void setDlcomment(String dlcomment) {
		this.dlcomment = dlcomment;
	}
	public String getReceptiontypecode() {
		return receptiontypecode;
	}
	public void setReceptiontypecode(String receptiontypecode) {
		this.receptiontypecode = receptiontypecode;
	}
	public String getReceptiontypedesc() {
		return receptiontypedesc;
	}
	public void setReceptiontypedesc(String receptiontypedesc) {
		this.receptiontypedesc = receptiontypedesc;
	}
	public String getStchangedate() {
		return stchangedate;
	}
	public void setStchangedate(String stchangedate) {
		this.stchangedate = stchangedate;
	}
	public String getDispatcheremail() {
		return dispatcheremail;
	}
	public void setDispatcheremail(String dispatcheremail) {
		this.dispatcheremail = dispatcheremail;
	}
	public Long getCouriertag() {
		return couriertag;
	}
	public void setCouriertag(Long couriertag) {
		this.couriertag = couriertag;
	}
	public String getSendnumber() {
		return sendnumber;
	}
	public void setSendnumber(String sendnumber) {
		this.sendnumber = sendnumber;
	}
	public Long getWithdrawalnumber() {
		return withdrawalnumber;
	}
	public void setWithdrawalnumber(Long withdrawalnumber) {
		this.withdrawalnumber = withdrawalnumber;
	}
	public String getWithdrawaldate() {
		return withdrawaldate;
	}
	public void setWithdrawaldate(String withdrawaldate) {
		this.withdrawaldate = withdrawaldate;
	}
	public Long getFirstwithdrawalnumber() {
		return firstwithdrawalnumber;
	}
	public void setFirstwithdrawalnumber(Long firstwithdrawalnumber) {
		this.firstwithdrawalnumber = firstwithdrawalnumber;
	}
	public String getFirstwithdrawaldate() {
		return firstwithdrawaldate;
	}
	public void setFirstwithdrawaldate(String firstwithdrawaldate) {
		this.firstwithdrawaldate = firstwithdrawaldate;
	}
	public String getPenultimatestate() {
		return penultimatestate;
	}
	public void setPenultimatestate(String penultimatestate) {
		this.penultimatestate = penultimatestate;
	}
	public String getUltimatestate() {
		return ultimatestate;
	}
	public void setUltimatestate(String ultimatestate) {
		this.ultimatestate = ultimatestate;
	}
	public Long getReschedulereasonid() {
		return reschedulereasonid;
	}
	public void setReschedulereasonid(Long reschedulereasonid) {
		this.reschedulereasonid = reschedulereasonid;
	}
	public String getReschedulereasoncode() {
		return reschedulereasoncode;
	}
	public void setReschedulereasoncode(String reschedulereasoncode) {
		this.reschedulereasoncode = reschedulereasoncode;
	}
	public String getReschedulereasondesc() {
		return reschedulereasondesc;
	}
	public void setReschedulereasondesc(String reschedulereasondesc) {
		this.reschedulereasondesc = reschedulereasondesc;
	}
	public Boolean getCourierreceived() {
		return courierreceived;
	}
	public void setCourierreceived(Boolean courierreceived) {
		this.courierreceived = courierreceived;
	}
		
}