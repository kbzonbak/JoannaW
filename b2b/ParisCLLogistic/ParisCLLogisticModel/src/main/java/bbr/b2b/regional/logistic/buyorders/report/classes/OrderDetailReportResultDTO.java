package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class OrderDetailReportResultDTO extends BaseResultDTO {

	private OrderDetailReportDataDTO[] orderdetail;
	private OrderDetailReportTotalDataDTO total;
	private PageInfoDTO pageInfo;
	private OrderReportDataDTO order;
	
	public OrderDetailReportDataDTO[] getOrderdetail() {
		return orderdetail;
	}
	public void setOrderdetail(OrderDetailReportDataDTO[] orderdetail) {
		this.orderdetail = orderdetail;
	}
	public OrderDetailReportTotalDataDTO getTotal() {
		return total;
	}
	public void setTotal(OrderDetailReportTotalDataDTO total) {
		this.total = total;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
	public OrderReportDataDTO getOrder() {
		return order;
	}
	public void setOrder(OrderReportDataDTO order) {
		this.order = order;
	}
}
