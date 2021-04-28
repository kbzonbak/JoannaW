package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevFineReportArrayResultDTO extends BaseResultDTO{

	private KPIvevVendorDimensionDTO[] vendors;
	private KPIvevDepartmentDimensionDTO[] departments;
	private KPIvevFineDataDTO[] finedata;
			
	public KPIvevVendorDimensionDTO[] getVendors() {
		return vendors;
	}
	public void setVendors(KPIvevVendorDimensionDTO[] vendors) {
		this.vendors = vendors;
	}
	public KPIvevDepartmentDimensionDTO[] getDepartments() {
		return departments;
	}
	public void setDepartments(KPIvevDepartmentDimensionDTO[] departments) {
		this.departments = departments;
	}
	public KPIvevFineDataDTO[] getFinedata() {
		return finedata;
	}
	public void setFinedata(KPIvevFineDataDTO[] finedata) {
		this.finedata = finedata;
	}
								
}
