package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrOrderToDeliveryUnitsResultDTO extends BaseResultDTO {

	private DvrOrderToDeliveryUnitsDTO[] dvrordertodeliveryunits;

	public DvrOrderToDeliveryUnitsDTO[] getDvrordertodeliveryunits() {
		return dvrordertodeliveryunits;
	}

	public void setDvrordertodeliveryunits(DvrOrderToDeliveryUnitsDTO[] dvrordertodeliveryunits) {
		this.dvrordertodeliveryunits = dvrordertodeliveryunits;
	}

}
