package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DOTaxDocumentReportResultDTO extends BaseResultDTO{

	private DOTaxDocumentReportDataDTO[] dotaxdocuments;
	private PageInfoDTO pageInfo;
	
	public DOTaxDocumentReportDataDTO[] getDotaxdocuments() {
		return dotaxdocuments;
	}
	public void setDotaxdocuments(DOTaxDocumentReportDataDTO[] dotaxdocuments) {
		this.dotaxdocuments = dotaxdocuments;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
		
}
