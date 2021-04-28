package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DeliveryStateTypeArrayResultDTO extends BaseResultDTO {

	private DeliveryStateTypeDTO[] deliverystatetypes;

	public DeliveryStateTypeDTO[] getDeliverystatetypes() {
		return deliverystatetypes;
	}

	public void setDeliverystatetypes(DeliveryStateTypeDTO[] deliverystatetypes) {
		this.deliverystatetypes = deliverystatetypes;
	}

}
