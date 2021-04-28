package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvrOrderDetailReportResultDTO extends BaseResultDTO {

	private DvrOrderReportDataDTO dvrorder;
	private DvrOrderDetailDataDTO[] dvrorderdetail;
	private DvrOrderDetailReportTotalDataDTO total;
	private PageInfoDTO pageInfo;

	public DvrOrderReportDataDTO getDvrorder() {
		return dvrorder;
	}

	public void setDvrorder(DvrOrderReportDataDTO dvrorder) {
		this.dvrorder = dvrorder;
	}

	public DvrOrderDetailDataDTO[] getDvrorderdetail() {
		return dvrorderdetail;
	}

	public void setDvrorderdetail(DvrOrderDetailDataDTO[] dvrorderdetail) {
		this.dvrorderdetail = dvrorderdetail;
	}

	public DvrOrderDetailReportTotalDataDTO getTotal() {
		return total;
	}

	public void setTotal(DvrOrderDetailReportTotalDataDTO total) {
		this.total = total;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
