package bbr.b2b.logistic.ddcdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class AddDODeliveryOfDirectOrdersWSResultDTO extends BaseResultDTO {

	private AddDODeliveryOfDirectOrdersWSResultDetailDTO[] details;

	public AddDODeliveryOfDirectOrdersWSResultDetailDTO[] getDetails() {
		return details;
	}

	public void setDetails(AddDODeliveryOfDirectOrdersWSResultDetailDTO[] details) {
		this.details = details;
	}

}
