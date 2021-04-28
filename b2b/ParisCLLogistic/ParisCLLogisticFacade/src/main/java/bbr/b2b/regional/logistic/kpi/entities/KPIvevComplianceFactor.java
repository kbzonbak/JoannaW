package bbr.b2b.regional.logistic.kpi.entities;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevComplianceFactor;

public class KPIvevComplianceFactor implements IKPIvevComplianceFactor{
	
	private Long id;
	private Double min;
	private Double max;
	private Double factor;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public Double getFactor() {
		return factor;
	}
	public void setFactor(Double factor) {
		this.factor = factor;
	}
	
}