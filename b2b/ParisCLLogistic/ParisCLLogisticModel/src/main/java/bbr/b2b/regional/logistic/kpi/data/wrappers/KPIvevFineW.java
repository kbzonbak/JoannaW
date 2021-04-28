package bbr.b2b.regional.logistic.kpi.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevFine;

public class KPIvevFineW extends ElementDTO implements IKPIvevFine{

	private Double totalfinefactor;
	private Double compliance;
	private Double finalfine;
	private Long kpivevfineperiodid;
	private Long vendorid;
	private Long departmentid;
	
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
	public Long getKpivevfineperiodid() {
		return kpivevfineperiodid;
	}
	public void setKpivevfineperiodid(Long kpivevfineperiodid) {
		this.kpivevfineperiodid = kpivevfineperiodid;
	}
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
	
}