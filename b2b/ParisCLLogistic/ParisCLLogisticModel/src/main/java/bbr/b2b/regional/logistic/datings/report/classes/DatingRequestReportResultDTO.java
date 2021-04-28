package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DatingRequestReportResultDTO extends BaseResultDTO {

	private DatingRequestReportDataDTO[] datingrequestreport;
	private PageInfoDTO pageinfo;
	
	public DatingRequestReportDataDTO[] getDatingrequestreport() {
		return datingrequestreport;
	}
	public void setDatingrequestreport(DatingRequestReportDataDTO[] datingrequestreport) {
		this.datingrequestreport = datingrequestreport;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}	
}
