package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class KPIvevCDDetailReportArrayResultDTO extends BaseResultDTO{
	
	private KPIvevCDDetailReportDTO[] kpivevcddetailreport;
	private PageInfoDTO pageinfo;

	public KPIvevCDDetailReportDTO[] getKpivevcddetailreport() {
		return kpivevcddetailreport;
	}

	public void setKpivevcddetailreport(KPIvevCDDetailReportDTO[] kpivevcddetailreport) {
		this.kpivevcddetailreport = kpivevcddetailreport;
	}

	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}

}
