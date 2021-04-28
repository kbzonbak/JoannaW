package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevPDTypeArrayResultDTO extends BaseResultDTO{

	private KPIvevPDTypeDTO[] types;

	public KPIvevPDTypeDTO[] getTypes() {
		return types;
	}

	public void setTypes(KPIvevPDTypeDTO[] types) {
		this.types = types;
	}
	
}