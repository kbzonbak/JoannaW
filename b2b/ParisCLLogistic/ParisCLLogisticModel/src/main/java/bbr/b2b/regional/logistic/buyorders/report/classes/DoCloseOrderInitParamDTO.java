package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class DoCloseOrderInitParamDTO extends UserDataInitParamDTO {

	private Long[] ordersid;
	private String vendorcode;//rut del prov

	public Long[] getOrdersid() {
		return ordersid;
	}

	public void setOrdersid(Long[] ordersid) {
		this.ordersid = ordersid;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}


}
