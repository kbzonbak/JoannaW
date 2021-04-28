package bbr.b2b.regional.logistic.kpi.entities;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevFine;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class KPIvevFine implements IKPIvevFine{
	
	private Long id;
	private Double totalfinefactor;
	private Double compliance;
	private Double finalfine;
	private KPIvevFinePeriod kpivevfineperiod;
	private Vendor vendor;
	private Department department;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getTotalfinefactor() {
		return totalfinefactor;
	}
	public void setTotalfinefactor(Double totalfinefactor) {
		this.totalfinefactor = totalfinefactor;
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
	public KPIvevFinePeriod getKpivevfineperiod() {
		return kpivevfineperiod;
	}
	public void setKpivevfineperiod(KPIvevFinePeriod kpivevfineperiod) {
		this.kpivevfineperiod = kpivevfineperiod;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

}