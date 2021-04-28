package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class BlockedOrderArrayResultDTO extends BaseResultDTO {

	private BlockedOrderDTO[] orderdata;

	public BlockedOrderDTO[] getOrderdata() {
		return orderdata;
	}

	public void setOrderdata(BlockedOrderDTO[] orderdata) {
		this.orderdata = orderdata;
	}
	
}
