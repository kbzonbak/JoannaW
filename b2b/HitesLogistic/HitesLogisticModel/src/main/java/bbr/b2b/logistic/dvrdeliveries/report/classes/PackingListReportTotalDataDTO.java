package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class PackingListReportTotalDataDTO implements Serializable {

	private Integer totalreg;
	private Double totalunits;

	public Integer getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}

	public Double getTotalunits() {
		return totalunits;
	}

	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}

}
