package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DepartmentArrayResultDTO extends BaseResultDTO{

	private DepartmentDTO[] departments;

	public DepartmentDTO[] getDepartments() {
		return departments;
	}

	public void setDepartments(DepartmentDTO[] departments) {
		this.departments = departments;
	}
	
}
