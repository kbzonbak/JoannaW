package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevComplianceFactorInitParamDTO implements Serializable{

	KPIvevComplianceFactorDTO[] compliancefactors;

	public KPIvevComplianceFactorDTO[] getCompliancefactors() {
		return compliancefactors;
	}

	public void setCompliancefactors(KPIvevComplianceFactorDTO[] compliancefactors) {
		this.compliancefactors = compliancefactors;
	}
	
}
