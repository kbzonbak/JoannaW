package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class VendorWithoutInvoiceValidationUploadResultDTO extends BaseResultDTO {

	private BaseResultDTO[] validationerrors;

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
}
