package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class KPIvevPDDetailReportArrayResultDTO extends BaseResultDTO{
	
	private KPIvevPDDetailReportDTO[] kpivevpddetailreport;
	private PageInfoDTO pageinfo;
	
	public KPIvevPDDetailReportDTO[] getKpivevpddetailreport() {
		return kpivevpddetailreport;
	}
	public void setKpivevpddetailreport(KPIvevPDDetailReportDTO[] kpivevpddetailreport) {
		this.kpivevpddetailreport = kpivevpddetailreport;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}

}