package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ICourierTag extends IElement {
	
	public Date getStartdate();

	public void setStartdate(Date startdate);

	public String getUser1();

	public void setUser1(String user1);

	public String getSendnumber();
	
	public Long getWithdrawalnumber();

	public void setWithdrawalnumber(Long withdrawalnumber);

	public void setSendnumber(String sendnumber);
}
