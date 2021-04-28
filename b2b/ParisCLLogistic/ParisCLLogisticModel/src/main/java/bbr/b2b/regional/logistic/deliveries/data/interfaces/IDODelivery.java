package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDODelivery extends IElement {

	public Long getNumber();
	public Date getOriginaldate();
	public Date getCommiteddate();
	public Date getCurrentstdate();
	public String getCurrentstwho();
	public String getCurrentstcomment();
	public String getDispatcheremail();
	public void setNumber(Long number);
	public void setOriginaldate(Date originaldate);
	public void setCommiteddate(Date commiteddate);
	public void setCurrentstdate(Date currentstdate);
	public void setCurrentstwho(String currentstwho);
	public void setCurrentstcomment(String currentstcomment);
	public void setDispatcheremail(String dispatcheremail);
	public Long getCouriertag();
	public void setCouriertag(Long couriertag);
}
