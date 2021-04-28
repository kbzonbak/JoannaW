package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class TaxDocumentIdsByPagesDTO extends BaseResultDTO {

	private Long[] taxdocumentids;

	public Long[] getTaxdocumentids() {
		return taxdocumentids;
	}

	public void setTaxdocumentids(Long[] taxdocumentids) {
		this.taxdocumentids = taxdocumentids;
	}
}
