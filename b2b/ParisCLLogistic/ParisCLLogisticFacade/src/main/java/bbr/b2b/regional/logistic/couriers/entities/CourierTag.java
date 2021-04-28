package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierTag;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;

public class CourierTag implements ICourierTag {
	
	private Long id;
	private Date startdate;
	private String user1;
	private String sendnumber;	
	private Long withdrawalnumber;
	private Date withdrawaldate;
	private DODelivery dodelivery;
	private RescheduleReason reschedulereason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	public DODelivery getDodelivery() {
		return dodelivery;
	}

	public void setDodelivery(DODelivery dodelivery) {
		this.dodelivery = dodelivery;
	}

	public RescheduleReason getReschedulereason() {
		return reschedulereason;
	}

	public void setReschedulereason(RescheduleReason reschedulereason) {
		this.reschedulereason = reschedulereason;
	}

}
