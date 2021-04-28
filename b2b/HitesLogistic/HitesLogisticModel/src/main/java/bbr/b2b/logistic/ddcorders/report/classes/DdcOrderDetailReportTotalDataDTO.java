package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;

public class DdcOrderDetailReportTotalDataDTO implements Serializable {

	private Integer totalreg;
	private Double needunits;
	private Double totalneed;
	
	public Integer getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}
	public Double getNeedunits() {
		return needunits;
	}
	public void setNeedunits(Double needunits) {
		this.needunits = needunits;
	}
	public Double getTotalneed() {
		return totalneed;
	}
	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}

}
