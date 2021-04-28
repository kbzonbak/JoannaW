package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DiscountByOrderResultReportDTO extends BaseResultDTO {

	private DiscountByOrderDataDTO[] discountbyorderdata;

	public DiscountByOrderDataDTO[] getDiscountbyorderdata() {
		return discountbyorderdata;
	}

	public void setDiscountbyorderdata(DiscountByOrderDataDTO[] discountbyorderdata) {
		this.discountbyorderdata = discountbyorderdata;
	}

}
