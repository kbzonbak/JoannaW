package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class SchedulePendingOrderArrayResultDTO extends BaseResultDTO {

	private SchedulePendingOrderDTO[] orderdata;
	
	public SchedulePendingOrderDTO[] getOrderdata() {
		return orderdata;
	}
	public void setOrderdata(SchedulePendingOrderDTO[] orderdata) {
		this.orderdata = orderdata;
	}

}
