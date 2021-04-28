package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOInvoicePendingCSVReportInitParamDTO implements Serializable {

	private String since;
	private String until;
	
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
