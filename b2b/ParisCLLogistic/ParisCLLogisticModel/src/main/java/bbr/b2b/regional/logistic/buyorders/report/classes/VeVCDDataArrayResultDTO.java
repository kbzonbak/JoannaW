package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVCDDataArrayResultDTO extends BaseResultDTO{

	private VeVCDDataDTO[] data;
	private BaseResultDTO[] validationerrors;
	
	public VeVCDDataDTO[] getData() {
		return data;
	}
	public void setData(VeVCDDataDTO[] data) {
		this.data = data;
	}
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}
	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
	
}
