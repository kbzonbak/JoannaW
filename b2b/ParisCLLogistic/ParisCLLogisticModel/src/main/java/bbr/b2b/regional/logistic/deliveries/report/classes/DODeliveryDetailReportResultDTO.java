package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DODeliveryDetailReportResultDTO extends BaseResultDTO {

	private DODeliveryDetailReportDataDTO[] orderdeliverydetail = null;
	private DODeliveryDetailReportTotalDataDTO totals = null;
	private PageInfoDTO pageInfo = null;
	
	public DODeliveryDetailReportTotalDataDTO getTotals() {
		return totals;
	}
	public void setTotals(DODeliveryDetailReportTotalDataDTO totals) {
		this.totals = totals;
	}
	public DODeliveryDetailReportDataDTO[] getOrderdeliverydetail() {
		return orderdeliverydetail;
	}
	public void setOrderdeliverydetail(DODeliveryDetailReportDataDTO[] orderdeliverydetail) {
		this.orderdeliverydetail = orderdeliverydetail;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}		
}
