package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ExcelDatingReportInitParamDTO implements Serializable{
	
	private String locationcode;
	private String since;
	private String until;
	
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getSince() {
		return since;
	}
	public void setSince(String since) {
		this.since = since;
	}
	public String getUntil() {
		return until;
	}
	public void setUntil(String until) {
		this.until = until;
	}
}
