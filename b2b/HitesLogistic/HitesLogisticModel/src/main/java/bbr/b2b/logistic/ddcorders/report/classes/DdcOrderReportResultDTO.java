package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DdcOrderReportResultDTO extends BaseResultDTO {

	private DdcOrderReportDataDTO[] ddcorders;
	private Integer totalreg;
	private PageInfoDTO pageInfo;

	public DdcOrderReportDataDTO[] getDdcorders() {
		return ddcorders;
	}

	public void setDdcorders(DdcOrderReportDataDTO[] ddcorders) {
		this.ddcorders = ddcorders;
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
