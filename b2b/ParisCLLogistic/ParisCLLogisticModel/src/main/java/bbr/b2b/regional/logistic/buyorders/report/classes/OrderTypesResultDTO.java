package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class OrderTypesResultDTO extends BaseResultDTO {

	public OrderTypeDTO[] ordertypes;

	public OrderTypeDTO[] getOrdertypes() {
		return ordertypes;
	}

	public void setOrdertypes(OrderTypeDTO[] ordertypes) {
		this.ordertypes = ordertypes;
	}	
}
