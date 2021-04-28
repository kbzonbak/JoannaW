package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class ReceptionReportResultDTO extends BaseResultDTO {

	private ReceptionReportDataDTO[] receptions;
	private PageInfoDTO pageInfo;
	
	public ReceptionReportDataDTO[] getReceptions() {
		return receptions;
	}
	public void setReceptions(ReceptionReportDataDTO[] receptions) {
		this.receptions = receptions;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
}
