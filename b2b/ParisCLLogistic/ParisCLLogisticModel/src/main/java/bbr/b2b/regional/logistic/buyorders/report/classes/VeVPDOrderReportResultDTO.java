package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VeVPDOrderReportResultDTO extends BaseResultDTO {

	private VeVPDOrderReportDataDTO[] orders;
	private VeVPDOrderReportTotalDataDTO totals;
	private PageInfoDTO pageInfo;

	public VeVPDOrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(VeVPDOrderReportDataDTO[] orders) {
		this.orders = orders;
	}

	public VeVPDOrderReportTotalDataDTO getTotals() {
		return totals;
	}

	public void setTotals(VeVPDOrderReportTotalDataDTO totals) {
		this.totals = totals;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
}
