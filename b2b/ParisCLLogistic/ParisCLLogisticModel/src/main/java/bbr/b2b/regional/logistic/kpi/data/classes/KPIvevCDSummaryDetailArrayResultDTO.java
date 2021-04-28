package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class KPIvevCDSummaryDetailArrayResultDTO extends BaseResultDTO{
	
	private KPIvevCDSummaryDetailReportDTO[] summarydetail;
	private PageInfoDTO pageinfo;
	
	public KPIvevCDSummaryDetailReportDTO[] getSummarydetail() {
		return summarydetail;
	}
	public void setSummarydetail(KPIvevCDSummaryDetailReportDTO[] summarydetail) {
		this.summarydetail = summarydetail;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}
}
