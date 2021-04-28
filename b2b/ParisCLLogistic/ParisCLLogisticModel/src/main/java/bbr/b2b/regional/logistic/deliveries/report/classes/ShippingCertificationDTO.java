package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class ShippingCertificationDTO implements Serializable {
	
	private String parentnumber;
	private String clientcode;
	private String user;
	private Double weight;
	private String description;
	private Long number;
	private String requestnumber;
	private Long withdrawalnumber;
	private String withdrawaldate;
	private Long reschedulereasonid;
	
	public String getParentnumber() {
		return parentnumber;
	}
	public void setParentnumber(String parentnumber) {
		this.parentnumber = parentnumber;
	}
	public String getClientcode() {
		return clientcode;
	}
	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
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
	public Long getReschedulereasonid() {
		return reschedulereasonid;
	}
	public void setReschedulereasonid(Long reschedulereasonid) {
		this.reschedulereasonid = reschedulereasonid;
	}
				
}
