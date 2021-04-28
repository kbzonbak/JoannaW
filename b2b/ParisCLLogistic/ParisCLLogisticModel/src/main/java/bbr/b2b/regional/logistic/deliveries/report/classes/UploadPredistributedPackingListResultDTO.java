package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class UploadPredistributedPackingListResultDTO extends BaseResultDTO {

	private UploadPredistributedPackingListDataDTO[] data;
	private BaseResultDTO[] validationerrors;
	
	public UploadPredistributedPackingListDataDTO[] getData() {
		return data;
	}
	public void setData(UploadPredistributedPackingListDataDTO[] data) {
		this.data = data;
	}
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}
	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
		
}
