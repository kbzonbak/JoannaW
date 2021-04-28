package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class RankingDTO implements Serializable {

	private Long id;
	private String when;
	private String since;
	private String until;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWhen() {
		return when;
	}
	public void setWhen(String when) {
		this.when = when;
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
