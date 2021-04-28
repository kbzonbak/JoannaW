package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ICourierScheduleLog extends IElement {

	public Date getWhen();
	public void setWhen(Date when);
	public String getUser();
	public void setUser(String user);
	public Long getWithdrawalnumber();
	public void setWithdrawalnumber(Long withdrawalnumber);
	public Date getWithdrawaldate();
	public void setWithdrawaldate(Date withdrawaldate);
	
}
