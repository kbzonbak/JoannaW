package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.util.Map;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class TaxDocumentAddResultDTO extends BaseResultDTO {

	private Map<Long, Long> validatedtaxdocuments;
	private Map<Long, Long> pendingtaxdocuments;
	private BaseResultDTO[] validationerrors;
			
	public Map<Long, Long> getValidatedtaxdocuments() {
		return validatedtaxdocuments;
	}
	public void setValidatedtaxdocuments(Map<Long, Long> validatedtaxdocuments) {
		this.validatedtaxdocuments = validatedtaxdocuments;
	}
	public Map<Long, Long> getPendingtaxdocuments() {
		return pendingtaxdocuments;
	}
	public void setPendingtaxdocuments(Map<Long, Long> pendingtaxdocuments) {
		this.pendingtaxdocuments = pendingtaxdocuments;
	}
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}
	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
}
