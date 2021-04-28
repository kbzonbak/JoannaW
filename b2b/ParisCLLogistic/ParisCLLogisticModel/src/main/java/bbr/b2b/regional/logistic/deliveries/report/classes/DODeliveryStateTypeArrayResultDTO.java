package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DODeliveryStateTypeArrayResultDTO extends BaseResultDTO {

	private DODeliveryStateTypeDTO[] dodeliverystatetypes = null;

	public DODeliveryStateTypeDTO[] getDodeliverystatetypes() {
		return dodeliverystatetypes;
	}

	public void setDodeliverystatetypes(DODeliveryStateTypeDTO[] dodeliverystatetypes) {
		this.dodeliverystatetypes = dodeliverystatetypes;
	}
}
