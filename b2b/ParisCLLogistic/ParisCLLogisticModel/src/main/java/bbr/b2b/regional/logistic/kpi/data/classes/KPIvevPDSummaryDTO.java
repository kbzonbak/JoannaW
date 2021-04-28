package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevPDSummaryDTO implements Serializable {
	
	private String month;
	private Integer year;
	private String since;
	private String until;
	private Double compliancerate;
	private Double vendordefaultrate;
	private Double courierdefaultrate;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
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
