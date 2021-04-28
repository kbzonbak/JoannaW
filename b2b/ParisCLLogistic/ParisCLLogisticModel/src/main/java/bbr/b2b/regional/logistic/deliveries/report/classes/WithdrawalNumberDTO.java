package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class WithdrawalNumberDTO implements Serializable {

	private Long dodeliveryid;
	private Long withdrawalnumber;
	private String withdrawaldate;
	private Long reschedulereasonid;
	
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
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
