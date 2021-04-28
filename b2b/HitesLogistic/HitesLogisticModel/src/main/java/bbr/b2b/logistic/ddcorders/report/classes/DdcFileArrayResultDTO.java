package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DdcFileArrayResultDTO extends BaseResultDTO {

	private DdcFileDTO[] ddcfiles;

	public DdcFileDTO[] getDdcfiles() {
		return ddcfiles;
	}

	public void setDdcfiles(DdcFileDTO[] ddcfiles) {
		this.ddcfiles = ddcfiles;
	}	
}
