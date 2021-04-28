package bbr.b2b.regional.logistic.directorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VeVPDDataArrayResultDTO extends BaseResultDTO{

	private VeVPDDataDTO[] data;
	private BaseResultDTO[] validationerrors;
	
	public VeVPDDataDTO[] getData() {
		return data;
	}
	public void setData(VeVPDDataDTO[] data) {
		this.data = data;
	}
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}
	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
	
}