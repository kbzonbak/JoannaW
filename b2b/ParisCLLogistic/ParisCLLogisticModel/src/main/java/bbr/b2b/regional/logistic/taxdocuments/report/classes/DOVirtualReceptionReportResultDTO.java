package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DOVirtualReceptionReportResultDTO extends BaseResultDTO{

	private DOVirtualReceptionReportDataDTO[] virtualreceptionreport;
	private PageInfoDTO pageInfo;
	
	public DOVirtualReceptionReportDataDTO[] getVirtualreceptionreport() {
		return virtualreceptionreport;
	}
	public void setVirtualreceptionreport(DOVirtualReceptionReportDataDTO[] virtualreceptionreport) {
		this.virtualreceptionreport = virtualreceptionreport;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
