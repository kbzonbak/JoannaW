package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DOTaxDocumentAddResultDTO extends BaseResultDTO{

	private Long ticketnumber;
	private DOTaxDocumentResultBean[] faileddeliveries;
	private DOTaxDocumentResultBean[] successdeliveries;
	private DOTaxDocumentResultBean[] validatingtaxdocuments;
	private BaseResultDTO[] validationerrors;
	private Boolean completeprocessing;

	public Long getTicketnumber() {
		return ticketnumber;
	}

	public void setTicketnumber(Long ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

	public DOTaxDocumentResultBean[] getFaileddeliveries() {
		return faileddeliveries;
	}

	public void setFaileddeliveries(DOTaxDocumentResultBean[] faileddeliveries) {
		this.faileddeliveries = faileddeliveries;
	}

	public DOTaxDocumentResultBean[] getSuccessdeliveries() {
		return successdeliveries;
	}

	public void setSuccessdeliveries(DOTaxDocumentResultBean[] successdeliveries) {
		this.successdeliveries = successdeliveries;
	}

	public DOTaxDocumentResultBean[] getValidatingtaxdocuments() {
		return validatingtaxdocuments;
	}

	public void setValidatingtaxdocuments(DOTaxDocumentResultBean[] validatingtaxdocuments) {
		this.validatingtaxdocuments = validatingtaxdocuments;
	}

	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}

	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}

	public Boolean getCompleteprocessing() {
		return completeprocessing;
	}

	public void setCompleteprocessing(Boolean completeprocessing) {
		this.completeprocessing = completeprocessing;
	}
		
}
