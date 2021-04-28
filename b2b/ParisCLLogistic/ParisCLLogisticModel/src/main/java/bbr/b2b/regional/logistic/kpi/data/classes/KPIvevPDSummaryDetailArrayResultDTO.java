package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class KPIvevPDSummaryDetailArrayResultDTO extends BaseResultDTO{
	
	private KPIvevPDSummaryDetailReportDTO[] summarydetail;
	private PageInfoDTO pageinfo;
	
	public KPIvevPDSummaryDetailReportDTO[] getSummarydetail() {
		return summarydetail;
	}
	public void setSummarydetail(KPIvevPDSummaryDetailReportDTO[] summarydetail) {
		this.summarydetail = summarydetail;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}
}
