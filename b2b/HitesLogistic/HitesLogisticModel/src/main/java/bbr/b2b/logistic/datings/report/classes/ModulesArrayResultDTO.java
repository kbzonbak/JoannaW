package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ModulesArrayResultDTO extends BaseResultDTO {

	private ModuleDataDTO[] modules = null;

	public ModuleDataDTO[] getModules() {
		return modules;
	}

	public void setModules(ModuleDataDTO[] modules) {
		this.modules = modules;
	}
}
