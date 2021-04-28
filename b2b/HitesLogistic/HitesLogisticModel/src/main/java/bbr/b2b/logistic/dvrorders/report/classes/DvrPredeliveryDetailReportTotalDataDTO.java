package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DvrPredeliveryDetailReportTotalDataDTO implements Serializable {

	private Integer totalreg;
	private Double totalunits;
	private Double pendingunits;
	private Double receivedunits;
	private Double todeliveryunits;

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

	public Double getPendingunits() {
		return pendingunits;
	}

	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}

	public Double getReceivedunits() {
		return receivedunits;
	}

	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}

	public Double getTodeliveryunits() {
		return todeliveryunits;
	}

	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}

}
