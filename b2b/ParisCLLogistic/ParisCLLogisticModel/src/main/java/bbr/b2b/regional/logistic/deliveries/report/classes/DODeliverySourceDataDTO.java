package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DODeliverySourceDataDTO implements Serializable {

	private Long dlnumber;
	private String deliverystatetype;
	private LocalDateTime currentdeliverydate;
	private Long docnumber;
	private String requestnumber;
	private String sendnumber;
	private Long withdrawalnumber;
	private LocalDateTime withdrawaldate;
	private Long firstwithdrawalnumber;
	private LocalDateTime firstwithdrawaldate;
	private LocalDateTime commiteddate;
	private LocalDateTime currentstdate;
	private String currentstcomment;
	private String clname;
	private String clrut;
	private String claddress;
	private String clcommune;
	private String clcity;
	private String clphone;
	private String internalcode;
	private Double availableunits;
	private Double receivedunits;
	private String dispatcheremail;
	private String penultimatestate;
	private String ultimatestate;

	public Long getDlnumber() {
		return dlnumber;
	}

	public void setDlnumber(Long dlnumber) {
		this.dlnumber = dlnumber;
	}

	public String getDeliverystatetype() {
		return deliverystatetype;
	}

	public void setDeliverystatetype(String deliverystatetype) {
		this.deliverystatetype = deliverystatetype;
	}

	public LocalDateTime getCurrentdeliverydate() {
		return currentdeliverydate;
	}

	public void setCurrentdeliverydate(LocalDateTime currentdeliverydate) {
		this.currentdeliverydate = currentdeliverydate;
	}

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

	public LocalDateTime getWithdrawaldate() {
		return withdrawaldate;
	}

	public void setWithdrawaldate(LocalDateTime withdrawaldate) {
		this.withdrawaldate = withdrawaldate;
	}

	public Long getFirstwithdrawalnumber() {
		return firstwithdrawalnumber;
	}

	public void setFirstwithdrawalnumber(Long firstwithdrawalnumber) {
		this.firstwithdrawalnumber = firstwithdrawalnumber;
	}

	public LocalDateTime getFirstwithdrawaldate() {
		return firstwithdrawaldate;
	}

	public void setFirstwithdrawaldate(LocalDateTime firstwithdrawaldate) {
		this.firstwithdrawaldate = firstwithdrawaldate;
	}

	public LocalDateTime getCommiteddate() {
		return commiteddate;
	}

	public void setCommiteddate(LocalDateTime commiteddate) {
		this.commiteddate = commiteddate;
	}

	public LocalDateTime getCurrentstdate() {
		return currentstdate;
	}

	public void setCurrentstdate(LocalDateTime currentstdate) {
		this.currentstdate = currentstdate;
	}

	public String getCurrentstcomment() {
		return currentstcomment;
	}

	public void setCurrentstcomment(String currentstcomment) {
		this.currentstcomment = currentstcomment;
	}

	public String getClname() {
		return clname;
	}

	public void setClname(String clname) {
		this.clname = clname;
	}

	public String getClrut() {
		return clrut;
	}

	public void setClrut(String clrut) {
		this.clrut = clrut;
	}

	public String getCladdress() {
		return claddress;
	}

	public void setCladdress(String claddress) {
		this.claddress = claddress;
	}

	public String getClcommune() {
		return clcommune;
	}

	public void setClcommune(String clcommune) {
		this.clcommune = clcommune;
	}

	public String getClcity() {
		return clcity;
	}

	public void setClcity(String clcity) {
		this.clcity = clcity;
	}

	public String getClphone() {
		return clphone;
	}

	public void setClphone(String clphone) {
		this.clphone = clphone;
	}

	public String getInternalcode() {
		return internalcode;
	}

	public void setInternalcode(String internalcode) {
		this.internalcode = internalcode;
	}

	public Double getAvailableunits() {
		return availableunits;
	}

	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}

	public Double getReceivedunits() {
		return receivedunits;
	}

	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}

	public String getDispatcheremail() {
		return dispatcheremail;
	}

	public void setDispatcheremail(String dispatcheremail) {
		this.dispatcheremail = dispatcheremail;
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

}
