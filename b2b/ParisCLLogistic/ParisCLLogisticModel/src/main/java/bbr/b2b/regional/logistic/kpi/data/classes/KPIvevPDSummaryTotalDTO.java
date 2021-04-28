package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevPDSummaryTotalDTO implements Serializable {

	private Integer periods;
	private Double compliancerate;
	private Double vendordefaultrate;
	private Double courierdefaultrate;
	
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
	public Double getVendordefaultrate() {
		return vendordefaultrate;
	}
	public void setVendordefaultrate(Double vendordefaultrate) {
		this.vendordefaultrate = vendordefaultrate;
	}
	public Double getCourierdefaultrate() {
		return courierdefaultrate;
	}
	public void setCourierdefaultrate(Double courierdefaultrate) {
		this.courierdefaultrate = courierdefaultrate;
	}
}
