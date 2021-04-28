package bbr.b2b.regional.logistic.items.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class FlowTypesInitParamDTO extends BaseResultDTO {

	public FlowTypeDTO[] flowtypes;

	public FlowTypeDTO[] getFlowtypes() {
		return flowtypes;
	}

	public void setFlowtypes(FlowTypeDTO[] flowtypes) {
		this.flowtypes = flowtypes;
	}

}
