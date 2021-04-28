package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DocksArrayResultDTO extends BaseResultDTO {

	private DockDTO[] docks = null;

	public DockDTO[] getDocks() {
		return docks;
	}

	public void setDocks(DockDTO[] docks) {
		this.docks = docks;
	}

}
