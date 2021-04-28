package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvrOrderReportResultDTO extends BaseResultDTO {

	private DvrOrderReportDataDTO[] dvrorders;
	private Integer totalreg;
	private PageInfoDTO pageInfo;

	public DvrOrderReportDataDTO[] getDvrorders() {
		return dvrorders;
	}

	public void setDvrorders(DvrOrderReportDataDTO[] dvrorders) {
		this.dvrorders = dvrorders;
	}

	public Integer getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
