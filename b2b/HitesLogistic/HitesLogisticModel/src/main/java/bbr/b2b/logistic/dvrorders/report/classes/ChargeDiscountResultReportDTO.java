package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ChargeDiscountResultReportDTO extends BaseResultDTO {

	private ChargeDiscountDvrOrderDetailDataDTO[] chargedata;
	private ChargeDiscountDvrOrderDetailDataDTO[] discountdata;

	public ChargeDiscountDvrOrderDetailDataDTO[] getChargedata() {
		return chargedata;
	}

	public void setChargedata(ChargeDiscountDvrOrderDetailDataDTO[] chargedata) {
		this.chargedata = chargedata;
	}

	public ChargeDiscountDvrOrderDetailDataDTO[] getDiscountdata() {
		return discountdata;
	}

	public void setDiscountdata(ChargeDiscountDvrOrderDetailDataDTO[] discountdata) {
		this.discountdata = discountdata;
	}

}
