package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierScheduleLog;

public class CourierScheduleLog implements ICourierScheduleLog {

	private Long id;
	private Date when;
	private String user;
	private Long withdrawalnumber;
	private Date withdrawaldate;
	private CourierTag couriertag;
	private RescheduleReason reschedulereason;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public CourierTag getCouriertag() {
		return couriertag;
	}
	public void setCouriertag(CourierTag couriertag) {
		this.couriertag = couriertag;
	}
	public RescheduleReason getReschedulereason() {
		return reschedulereason;
	}
	public void setReschedulereason(RescheduleReason reschedulereason) {
		this.reschedulereason = reschedulereason;
	}
	
}
