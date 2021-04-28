package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CheckDVHOrdersExecuteActionsResultDTO extends BaseResultDTO {

	private Boolean existsDeliveryToDelete;

	public Boolean getExistsDeliveryToDelete() {
		return existsDeliveryToDelete;
	}

	public void setExistsDeliveryToDelete(Boolean existsDeliveryToDelete) {
		this.existsDeliveryToDelete = existsDeliveryToDelete;
	}

}
