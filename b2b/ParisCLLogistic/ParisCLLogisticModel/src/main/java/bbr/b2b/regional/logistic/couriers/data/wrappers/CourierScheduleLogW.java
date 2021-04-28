package bbr.b2b.regional.logistic.couriers.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierScheduleLog;

public class CourierScheduleLogW extends ElementDTO implements ICourierScheduleLog {

	private Date when;
	private String user;
	private Long withdrawalnumber;
	private Date withdrawaldate;
	private Long couriertagid;
	private Long reschedulereasonid;
	
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Long getWithdrawalnumber() {
		return withdrawalnumber;
	}
	public void setWithdrawalnumber(Long withdrawalnumber) {
		this.withdrawalnumber = withdrawalnumber;
	}
	public Date getWithdrawaldate() {
		return withdrawaldate;
	}
	public void setWithdrawaldate(Date withdrawaldate) {
		this.withdrawaldate = withdrawaldate;
	}
	public Long getCouriertagid() {
		return couriertagid;
	}
	public void setCouriertagid(Long couriertagid) {
		this.couriertagid = couriertagid;
	}
	public Long getReschedulereasonid() {
		return reschedulereasonid;
	}
	public void setReschedulereasonid(Long reschedulereasonid) {
		this.reschedulereasonid = reschedulereasonid;
	}
		
}
