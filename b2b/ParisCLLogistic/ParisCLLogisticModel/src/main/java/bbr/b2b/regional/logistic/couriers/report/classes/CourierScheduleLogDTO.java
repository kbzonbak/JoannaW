package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

public class CourierScheduleLogDTO implements Serializable {

	private String when;
	private Long withdrawalnumber;
	private String withdrawaldate;
	private Long reschedulereasonid;
	private String reschedulereasoncode;
	private String reschedulereasondesc;
	
	public String getWhen() {
		return when;
	}
	public void setWhen(String when) {
		this.when = when;
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
	
}
