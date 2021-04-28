package bbr.b2b.regional.logistic.mobile.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DODeliveryItemsMobileResultDTO extends BaseResultDTO {
	
	private DODeliveryItemsMobileDataDTO[] itemsdata;

	
	public DODeliveryItemsMobileDataDTO[] getItemsdata() {
		return itemsdata;
	}
	public void setItemsdata(DODeliveryItemsMobileDataDTO[] itemsdata) {
		this.itemsdata = itemsdata;
	}
}
