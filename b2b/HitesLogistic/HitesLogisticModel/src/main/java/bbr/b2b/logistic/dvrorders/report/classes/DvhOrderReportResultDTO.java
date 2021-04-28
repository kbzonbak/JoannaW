package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvhOrderReportResultDTO extends BaseResultDTO {

	private DvhOrderReportDataDTO[] dvhorders;
	private Integer totalreg;
	private PageInfoDTO pageInfo;

	public DvhOrderReportDataDTO[] getDvhorders() {
		return dvhorders;
	}

	public void setDvhorders(DvhOrderReportDataDTO[] dvhorders) {
		this.dvhorders = dvhorders;
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
