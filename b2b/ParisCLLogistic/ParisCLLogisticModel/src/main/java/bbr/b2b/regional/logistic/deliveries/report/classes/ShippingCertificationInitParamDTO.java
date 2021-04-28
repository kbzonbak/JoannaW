package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class ShippingCertificationInitParamDTO extends UserDataInitParamDTO {
	
	private WithdrawalNumberDTO[] withdrawalnumbers;
	private String vendorcode;

	public WithdrawalNumberDTO[] getWithdrawalnumbers() {
		return withdrawalnumbers;
	}
	public void setWithdrawalnumbers(WithdrawalNumberDTO[] withdrawalnumbers) {
		this.withdrawalnumbers = withdrawalnumbers;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
		
}
