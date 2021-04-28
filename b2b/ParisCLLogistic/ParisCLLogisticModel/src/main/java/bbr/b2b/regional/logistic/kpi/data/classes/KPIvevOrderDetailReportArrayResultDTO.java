package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevOrderDetailReportArrayResultDTO extends BaseResultDTO{

	private KPIvevOrderDetailDataDTO[] orderdetaildata;
	
	public KPIvevOrderDetailDataDTO[] getOrderdetaildata() {
		return orderdetaildata;
	}

	public void setOrderdetaildata(KPIvevOrderDetailDataDTO[] orderdetaildata) {
		this.orderdetaildata = orderdetaildata;
	}
	
}
