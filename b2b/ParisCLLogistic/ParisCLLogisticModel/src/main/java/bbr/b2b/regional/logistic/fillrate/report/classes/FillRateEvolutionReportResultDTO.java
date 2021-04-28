package bbr.b2b.regional.logistic.fillrate.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class FillRateEvolutionReportResultDTO extends BaseResultDTO{

	private String vendorname;
	private String departmentname;
	private FillRateEvolutionReportDataDTO[] fillrateevolution = null;
		
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public FillRateEvolutionReportDataDTO[] getFillrateevolution() {
		return fillrateevolution;
	}
	public void setFillrateevolution(FillRateEvolutionReportDataDTO[] fillrateevolution) {
		this.fillrateevolution = fillrateevolution;
	}
	
}
