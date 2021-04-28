package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrDeliveryStateTypeResultDTO extends BaseResultDTO {

	private DvrDeliveryStateTypeDataDTO[] dvrdeliverystatetypedata;

	public DvrDeliveryStateTypeDataDTO[] getDvrdeliverystatetypedata() {
		return dvrdeliverystatetypedata;
	}

	public void setDvrdeliverystatetypedata(DvrDeliveryStateTypeDataDTO[] dvrdeliverystatetypedata) {
		this.dvrdeliverystatetypedata = dvrdeliverystatetypedata;
	}
}
