package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvrDeliveryReportResultDTO extends BaseResultDTO {

	private DvrDeliveryReportDataDTO[] dvrdeliveryreportdata;
	private Integer totalreg;
	private PageInfoDTO pageInfo;

	public DvrDeliveryReportDataDTO[] getDvrdeliveryreportdata() {
		return dvrdeliveryreportdata;
	}

	public void setDvrdeliveryreportdata(DvrDeliveryReportDataDTO[] dvrdeliveryreportdata) {
		this.dvrdeliveryreportdata = dvrdeliveryreportdata;
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
