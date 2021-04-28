package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DetailDiscountArrayResultDTO extends BaseResultDTO {

	private DetailDiscountDTO[] detaildiscount;

	public DetailDiscountDTO[] getDetaildiscount() {
		return detaildiscount;
	}

	public void setDetaildiscount(DetailDiscountDTO[] detaildiscount) {
		this.detaildiscount = detaildiscount;
	}

}
