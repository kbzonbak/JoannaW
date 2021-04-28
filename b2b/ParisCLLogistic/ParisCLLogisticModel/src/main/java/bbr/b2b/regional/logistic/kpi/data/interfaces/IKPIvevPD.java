package bbr.b2b.regional.logistic.kpi.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevPD extends IElement{

	public Date getFcm();
	public void setFcm(Date fcm);
	public Integer getTotaldelivered();
	public void setTotaldelivered(Integer totaldelivered);
	public Integer getTotalreceiveddelayed();
	public void setTotalreceiveddelayed(Integer totalreceiveddelayed);
	public Integer getTotalcourierreceiveddelayed();
	public void setTotalcourierreceiveddelayed(Integer totalcourierreceiveddelayed);
	public Integer getTotalproviderdelayed();
	public void setTotalproviderdelayed(Integer totalproviderdelayed);
	public Integer getTotalcourierdelayed();
	public void setTotalcourierdelayed(Integer totalcourierdelayed);
	public Integer getTotaldelivering();
	public void setTotaldelivering(Integer totaldelivering);
	public Integer getTotallosts();
	public void setTotallosts(Integer totallosts);
	public Integer getCreditnotes();
	public void setCreditnotes(Integer creditnotes);
	public Integer getInconsistencies();
	public void setInconsistencies(Integer inconsistencies);
	public Integer getCustomerresponsabilities();
	public void setCustomerresponsabilities(Integer customerresponsabilities);
	public Date getExecutiondate();
	public void setExecutiondate(Date executiondate);
	
}
