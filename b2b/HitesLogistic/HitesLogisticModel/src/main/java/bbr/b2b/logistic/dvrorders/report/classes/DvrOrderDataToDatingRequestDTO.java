package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DvrOrderDataToDatingRequestDTO implements Serializable {

	private Long dvrorderid;
	private Long dvrordernumber;
	private Double pendingunits;

	public Long getDvrorderid() {
		return dvrorderid;
	}

	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}

	public Long getDvrordernumber() {
		return dvrordernumber;
	}

	public void setDvrordernumber(Long dvrordernumber) {
		this.dvrordernumber = dvrordernumber;
	}

	public Double getPendingunits() {
		return pendingunits;
	}

	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}

}
