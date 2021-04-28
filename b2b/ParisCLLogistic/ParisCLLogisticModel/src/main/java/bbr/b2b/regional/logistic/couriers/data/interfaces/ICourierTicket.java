package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ICourierTicket extends IElement {

	public Long getTicketnumber();

	public void setTicketnumber(Long ticketnumber);

	public Date getCreationdate();

	public void setCreationdate(Date creationdate);

	public Date getStartdate();

	public void setStartdate(Date startdate);

	public Date getEnddate();

	public void setEnddate(Date enddate);

	public Long getUserid();

	public void setUserid(Long userid);

	public String getUsername();

	public void setUsername(String username);

	public String getUsermail();

	public void setUsermail(String usermail);

	public boolean isProcessed();

	public void setProcessed(boolean processed);
}
