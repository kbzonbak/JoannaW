package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevCDSummaryTotalDTO implements Serializable {

	private Integer periods;
	private Double compliancerate;
	private Double defaultrate;
	
	public Integer getPeriods() {
		return periods;
	}
	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
	public Double getCompliancerate() {
		return compliancerate;
	}
	public void setCompliancerate(Double compliancerate) {
		this.compliancerate = compliancerate;
	}
	public Double getDefaultrate() {
		return defaultrate;
	}
	public void setDefaultrate(Double defaultrate) {
		this.defaultrate = defaultrate;
	}
}
