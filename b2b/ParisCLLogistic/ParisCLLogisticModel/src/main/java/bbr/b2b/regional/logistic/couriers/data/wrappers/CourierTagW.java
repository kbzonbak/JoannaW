package bbr.b2b.regional.logistic.couriers.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierTag;

public class CourierTagW extends ElementDTO implements ICourierTag {
	
	private Date startdate;
	private String user1;
	private String sendnumber;	
	private Long withdrawalnumber;
	private Date withdrawaldate;
	private Long dodeliveryid;
	private Long reschedulereasonid;

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
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

	public Date getWithdrawaldate() {
		return withdrawaldate;
	}

	public void setWithdrawaldate(Date withdrawaldate) {
		this.withdrawaldate = withdrawaldate;
	}

	public Long getDodeliveryid() {
		return dodeliveryid;
	}

	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}

	public Long getReschedulereasonid() {
		return reschedulereasonid;
	}

	public void setReschedulereasonid(Long reschedulereasonid) {
		this.reschedulereasonid = reschedulereasonid;
	}
	
}
