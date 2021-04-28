package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VeVCDOrderReportResultDTO extends BaseResultDTO {

	private VeVCDOrderReportDataDTO[] orders;
	private VeVCDOrderReportTotalDataDTO totals;
	private PageInfoDTO pageInfo;

	public VeVCDOrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(VeVCDOrderReportDataDTO[] orders) {
		this.orders = orders;
	}

	public VeVCDOrderReportTotalDataDTO getTotals() {
		return totals;
	}

	public void setTotals(VeVCDOrderReportTotalDataDTO totals) {
		this.totals = totals;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
}
