package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class AddDODeliveryOfDirectOrdersInitParamDTO extends UserDataInitParamDTO {

	private DirectOrderReprogDateDTO[] data;
	private String vendorcode;

	public DirectOrderReprogDateDTO[] getData() {
		return data;
	}
	public void setData(DirectOrderReprogDateDTO[] data) {
		this.data = data;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}	
}
