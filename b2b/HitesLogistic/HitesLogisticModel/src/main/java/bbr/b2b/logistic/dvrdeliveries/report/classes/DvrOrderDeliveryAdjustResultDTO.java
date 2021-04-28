package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;

public class DvrOrderDeliveryAdjustResultDTO extends BaseResultsDTO {

	private Double totalavailableunits;

	public Double getTotalavailableunits() {
		return totalavailableunits;
	}

	public void setTotalavailableunits(Double totalavailableunits) {
		this.totalavailableunits = totalavailableunits;
	}	
}
