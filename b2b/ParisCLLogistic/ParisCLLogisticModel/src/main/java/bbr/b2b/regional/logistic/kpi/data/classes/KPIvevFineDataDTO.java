package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevFineDataDTO implements Serializable{

	private Long vendorid;
	private Long departmentid;
	private Double compliance;
	private Double finalfine;
	private Double currencyfine;
	
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public Double getCompliance() {
		return compliance;
	}
	public void setCompliance(Double compliance) {
		this.compliance = compliance;
	}
	public Double getFinalfine() {
		return finalfine;
	}
	public void setFinalfine(Double finalfine) {
		this.finalfine = finalfine;
	}
	public Double getCurrencyfine() {
		return currencyfine;
	}
	public void setCurrencyfine(Double currencyfine) {
		this.currencyfine = currencyfine;
	}
			
}