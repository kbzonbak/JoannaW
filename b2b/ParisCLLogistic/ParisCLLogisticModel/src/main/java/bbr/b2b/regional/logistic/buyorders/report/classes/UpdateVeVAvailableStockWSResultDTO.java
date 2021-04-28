package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class UpdateVeVAvailableStockWSResultDTO extends BaseResultDTO {

	private VeVAvailableStockWSResultDTO[] details;

	public VeVAvailableStockWSResultDTO[] getDetails() {
		return details;
	}

	public void setDetails(VeVAvailableStockWSResultDTO[] details) {
		this.details = details;
	}
		
}