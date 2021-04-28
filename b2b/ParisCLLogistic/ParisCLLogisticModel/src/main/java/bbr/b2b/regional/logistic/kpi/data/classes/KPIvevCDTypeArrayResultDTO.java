package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevCDTypeArrayResultDTO extends BaseResultDTO{

	private KPIvevCDTypeDTO[] types;

	public KPIvevCDTypeDTO[] getTypes() {
		return types;
	}

	public void setTypes(KPIvevCDTypeDTO[] types) {
		this.types = types;
	}
	
}
