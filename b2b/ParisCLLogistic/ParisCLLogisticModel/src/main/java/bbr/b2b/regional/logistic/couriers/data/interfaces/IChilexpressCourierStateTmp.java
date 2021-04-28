package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IChilexpressCourierStateTmp extends IElement {

	public String getFilename();
	public void setFilename(String filename);
	public Date getFiledate();
	public void setFiledate(Date filedate);
	public Date getWhen();
	public void setWhen(Date when);
	public Integer getLine();
	public void setLine(Integer line);
	public String getWorkordernumber();
	public void setWorkordernumber(String workordernumber);
	public String getWorkorderreference();
	public void setWorkorderreference(String workorderreference);
	public String getEventcode();
	public void setEventcode(String eventcode);
	public String getEvent();
	public void setEvent(String event);
	public String getEventdate();
	public void setEventdate(String eventdate);
	public String getEventtime();
	public void setEventtime(String eventtime);
	public Date getEventfulldate();
	public void setEventfulldate(Date eventfulldate);
		
}
