package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DOTaxDocumentStateTypeArrayResultDTO extends BaseResultDTO{

	private DOTaxDocumentStateTypeDTO[] dotaxdocumentstatetypes;

	public DOTaxDocumentStateTypeDTO[] getDotaxdocumentstatetypes() {
		return dotaxdocumentstatetypes;
	}

	public void setDotaxdocumentstatetypes(DOTaxDocumentStateTypeDTO[] dotaxdocumentstatetypes) {
		this.dotaxdocumentstatetypes = dotaxdocumentstatetypes;
	}
		
}
