package bbr.b2b.regional.logistic.kpi.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevFinePeriod;

public class KPIvevFinePeriodW extends ElementDTO implements IKPIvevFinePeriod{

	private Date executiondate;
	private Date since;
	private Date until;
	private String vevtype;
	
	public Date getExecutiondate() {
		return executiondate;
	}
	public void setExecutiondate(Date executiondate) {
		this.executiondate = executiondate;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
	public Date getUntil() {
		return until;
	}
	public void setUntil(Date until) {
		this.until = until;
	}
	public String getVevtype() {
		return vevtype;
	}
	public void setVevtype(String vevtype) {
		this.vevtype = vevtype;
	}
		
}