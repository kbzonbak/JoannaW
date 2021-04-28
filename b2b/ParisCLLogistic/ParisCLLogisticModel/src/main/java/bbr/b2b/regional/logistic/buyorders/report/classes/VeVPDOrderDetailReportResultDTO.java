package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VeVPDOrderDetailReportResultDTO extends BaseResultDTO {

	private VeVPDOrderDetailReportDataDTO[] orderdetail;
	private VeVPDOrderDetailReportTotalDataDTO total;
	private PageInfoDTO pageInfo;
	private VeVPDOrderReportDataDTO order;
	
	public VeVPDOrderDetailReportDataDTO[] getOrderdetail() {
		return orderdetail;
	}
	public void setOrderdetail(VeVPDOrderDetailReportDataDTO[] orderdetail) {
		this.orderdetail = orderdetail;
	}
	public VeVPDOrderDetailReportTotalDataDTO getTotal() {
		return total;
	}
	public void setTotal(VeVPDOrderDetailReportTotalDataDTO total) {
		this.total = total;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
	public VeVPDOrderReportDataDTO getOrder() {
		return order;
	}
	public void setOrder(VeVPDOrderReportDataDTO order) {
		this.order = order;
	}	
}
