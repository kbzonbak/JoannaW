package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class AddDeliveryOfOrdersAndFlowTypesResultDTO extends BaseResultDTO {

	private AddDeliveryOfOrdersAndFlowTypesDataDTO[] report;
	private BaseResultDTO[] validationerrors;

	public AddDeliveryOfOrdersAndFlowTypesDataDTO[] getReport() {
		return report;
	}

	public void setReport(AddDeliveryOfOrdersAndFlowTypesDataDTO[] report) {
		this.report = report;
	}

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
}
