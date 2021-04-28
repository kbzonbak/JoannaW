package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class TaxDocumentReportResultDTO extends BaseResultDTO {

	TaxDocumentReportDataDTO[] taxdocuments;

	public TaxDocumentReportDataDTO[] getTaxdocuments() {
		return taxdocuments;
	}

	public void setTaxdocuments(TaxDocumentReportDataDTO[] taxdocuments) {
		this.taxdocuments = taxdocuments;
	}
		
}
