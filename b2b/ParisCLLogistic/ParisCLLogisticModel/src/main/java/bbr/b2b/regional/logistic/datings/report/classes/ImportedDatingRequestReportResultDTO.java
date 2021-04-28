package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class ImportedDatingRequestReportResultDTO extends BaseResultDTO {

	private ImportedDatingRequestReportDataDTO[] datingrequestreport;
	private PageInfoDTO pageinfo;
	
	public ImportedDatingRequestReportDataDTO[] getDatingrequestreport() {
		return datingrequestreport;
	}
	public void setDatingrequestreport(ImportedDatingRequestReportDataDTO[] datingrequestreport) {
		this.datingrequestreport = datingrequestreport;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}	
}
