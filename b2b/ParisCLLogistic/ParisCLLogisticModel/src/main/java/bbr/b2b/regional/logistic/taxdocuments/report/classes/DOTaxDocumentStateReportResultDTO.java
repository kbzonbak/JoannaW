package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DOTaxDocumentStateReportResultDTO extends BaseResultDTO {

	private DOTaxDocumentStateDTO[] dotaxdocumentstates;
	private PageInfoDTO pageInfo;
	
	public DOTaxDocumentStateDTO[] getDotaxdocumentstates() {
		return dotaxdocumentstates;
	}
	public void setDotaxdocumentstates(DOTaxDocumentStateDTO[] dotaxdocumentstates) {
		this.dotaxdocumentstates = dotaxdocumentstates;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
		
}
