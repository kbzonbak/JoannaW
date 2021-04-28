package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DeliveryDetailReportResultDTO extends BaseResultDTO {

	private DeliveryDetailReportDataDTO[] orderdeliverydetail = null;
	private DeliveryDetailReportTotalDataDTO totals = null;
	private PageInfoDTO pageInfo = null;
	
	public DeliveryDetailReportDataDTO[] getOrderdeliverydetail() {
		return orderdeliverydetail;
	}
	public void setOrderdeliverydetail(DeliveryDetailReportDataDTO[] orderdeliverydetail) {
		this.orderdeliverydetail = orderdeliverydetail;
	}	
	public DeliveryDetailReportTotalDataDTO getTotals() {
		return totals;
	}
	public void setTotals(DeliveryDetailReportTotalDataDTO totals) {
		this.totals = totals;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}		
}
