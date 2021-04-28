package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DeliveryItemsArrayResultDTO extends BaseResultDTO {

	private String vendorcode;
	private List<String> iteminternalnames;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public List<String> getIteminternalnames() {
		return iteminternalnames;
	}
	public void setIteminternalnames(List<String> iteminternalnames) {
		this.iteminternalnames = iteminternalnames;
	}

}
