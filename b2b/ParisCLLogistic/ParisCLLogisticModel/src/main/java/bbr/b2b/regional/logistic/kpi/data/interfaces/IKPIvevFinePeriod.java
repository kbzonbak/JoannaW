package bbr.b2b.regional.logistic.kpi.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevFinePeriod extends IElement{

	public Date getExecutiondate();
	public void setExecutiondate(Date executiondate);
	public Date getSince();
	public void setSince(Date since);
	public Date getUntil();
	public void setUntil(Date until);
	public String getVevtype();
	public void setVevtype(String vevtype);
	
}