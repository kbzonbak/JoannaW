package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevCDDetailArrayResultDTO extends BaseResultDTO{
	
	private KPIvevCDDetailDTO[] kpivevecddetail;

	public KPIvevCDDetailDTO[] getKpivevecddetail() {
		return kpivevecddetail;
	}

	public void setKpivevecddetail(KPIvevCDDetailDTO[] kpivevecddetail) {
		this.kpivevecddetail = kpivevecddetail;
	}	

}
