package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevPDDetailArrayResultDTO extends BaseResultDTO{
	
	private KPIvevPDDetailDTO[] kpivevepddetail;

	public KPIvevPDDetailDTO[] getKpivevepddetail() {
		return kpivevepddetail;
	}

	public void setKpivevepddetail(KPIvevPDDetailDTO[] kpivevepddetail) {
		this.kpivevepddetail = kpivevepddetail;
	}

}