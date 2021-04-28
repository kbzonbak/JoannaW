package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVUpdateTypeArrayResultDTO extends BaseResultDTO{

	private VeVUpdateTypeDTO[] updatetypes = null;

	public VeVUpdateTypeDTO[] getUpdatetypes() {
		return updatetypes;
	}

	public void setUpdatetypes(VeVUpdateTypeDTO[] updatetypes) {
		this.updatetypes = updatetypes;
	}

}
