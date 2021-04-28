package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ValidateDeliveryItemsArrayResultDTO extends BaseResultDTO {

	private PendingItemDTO[] pendingitems;

	public PendingItemDTO[] getPendingitems() {
		return pendingitems;
	}

	public void setPendingitems(PendingItemDTO[] pendingitems) {
		this.pendingitems = pendingitems;
	}
		
}
