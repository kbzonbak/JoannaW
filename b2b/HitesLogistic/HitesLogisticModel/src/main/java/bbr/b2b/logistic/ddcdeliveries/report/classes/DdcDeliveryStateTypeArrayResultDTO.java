package bbr.b2b.logistic.ddcdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DdcDeliveryStateTypeArrayResultDTO extends BaseResultDTO {

	private DdcDeliveryStateTypeDataDTO[] ddcdeliverystatetypes;

	public DdcDeliveryStateTypeDataDTO[] getDdcdeliverystatetypes() {
		return ddcdeliverystatetypes;
	}

	public void setDdcdeliverystatetypes(DdcDeliveryStateTypeDataDTO[] ddcdeliverystatetypes) {
		this.ddcdeliverystatetypes = ddcdeliverystatetypes;
	}

}
