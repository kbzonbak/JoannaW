package bbr.b2b.regional.logistic.directorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DirectOrderStateTypeArrayResultDTO extends BaseResultDTO {

	private DirectOrderStateTypeDTO[] orderstatetypes = null;

	public DirectOrderStateTypeDTO[] getOrderstatetypes() {
		return orderstatetypes;
	}

	public void setOrderstatetypes(DirectOrderStateTypeDTO[] orderstatetypes) {
		this.orderstatetypes = orderstatetypes;
	}

}
