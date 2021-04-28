package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class OrderReportResultDTO extends BaseResultDTO {

	private OrderReportDataDTO[] orders;
	private OrderReportTotalDataDTO totals;
	private PageInfoDTO pageInfo;

	public OrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(OrderReportDataDTO[] orders) {
		this.orders = orders;
	}
	
	public OrderReportTotalDataDTO getTotals() {
		return totals;
	}

	public void setTotals(OrderReportTotalDataDTO totals) {
		this.totals = totals;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
}
