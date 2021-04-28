package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevComplianceFactorArrayResultDTO extends BaseResultDTO{

	KPIvevComplianceFactorDTO[] compliancefactors;

	public KPIvevComplianceFactorDTO[] getCompliancefactors() {
		return compliancefactors;
	}

	public void setCompliancefactors(KPIvevComplianceFactorDTO[] compliancefactors) {
		this.compliancefactors = compliancefactors;
	}
	
}
