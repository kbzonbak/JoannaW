package bbr.b2b.regional.logistic.kpi.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevFinePeriod;

public class KPIvevFinePeriod implements IKPIvevFinePeriod{
	
	private Long id;
	private Date executiondate;
	private Date since;
	private Date until;
	private String vevtype;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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