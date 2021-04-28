package bbr.b2b.regional.logistic.kpi.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevCD extends IElement{

	public Date getFpi();
	public void setFpi(Date fpi);
	public Integer getTotalreceivedconformed();
	public void setTotalreceivedconformed(Integer totalreceivedconformed);
	public Integer getTotalreceiveddelayed();
	public void setTotalreceiveddelayed(Integer totalreceiveddelayed);
	public Integer getTotalproviderdelayed();
	public void setTotalproviderdelayed(Integer totalproviderdelayed);
	public Integer getCreditnotes();
	public void setCreditnotes(Integer creditnotes);
	public Date getExecutiondate();
	public void setExecutiondate(Date executiondate);
	
}
