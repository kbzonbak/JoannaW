package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvrPredeliveryDetailReportResultDTO extends BaseResultDTO {

	private DvrPredeliveryDetailDataDTO[] dvrpredeliverydetail;
	private DvrPredeliveryDetailReportTotalDataDTO total;
	private PageInfoDTO pageInfo;

	public DvrPredeliveryDetailDataDTO[] getDvrpredeliverydetail() {
		return dvrpredeliverydetail;
	}

	public void setDvrpredeliverydetail(DvrPredeliveryDetailDataDTO[] dvrpredeliverydetail) {
		this.dvrpredeliverydetail = dvrpredeliverydetail;
	}

	public DvrPredeliveryDetailReportTotalDataDTO getTotal() {
		return total;
	}

	public void setTotal(DvrPredeliveryDetailReportTotalDataDTO total) {
		this.total = total;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
