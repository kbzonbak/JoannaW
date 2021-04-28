package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DatingRequestReportResultDTO extends BaseResultDTO {

	private DatingRequestReportDataDTO[] datingrequestreportdata;
	private Integer totalreg;
	private PageInfoDTO pageInfo;

	public DatingRequestReportDataDTO[] getDatingrequestreportdata() {
		return datingrequestreportdata;
	}

	public Integer getTotalreg() {
		return totalreg;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setDatingrequestreportdata(DatingRequestReportDataDTO[] datingrequestreportdata) {
		this.datingrequestreportdata = datingrequestreportdata;
	}

	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
