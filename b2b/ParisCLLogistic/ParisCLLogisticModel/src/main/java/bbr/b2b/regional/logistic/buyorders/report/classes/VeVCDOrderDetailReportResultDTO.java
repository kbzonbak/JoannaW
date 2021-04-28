package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VeVCDOrderDetailReportResultDTO extends BaseResultDTO {

	private VeVCDOrderDetailReportDataDTO[] orderdetail;
	private VeVCDOrderDetailReportTotalDataDTO total;
	private PageInfoDTO pageInfo;
	private VeVCDOrderReportDataDTO order;
	
	public VeVCDOrderDetailReportDataDTO[] getOrderdetail() {
		return orderdetail;
	}
	public void setOrderdetail(VeVCDOrderDetailReportDataDTO[] orderdetail) {
		this.orderdetail = orderdetail;
	}
	public VeVCDOrderDetailReportTotalDataDTO getTotal() {
		return total;
	}
	public void setTotal(VeVCDOrderDetailReportTotalDataDTO total) {
		this.total = total;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
	public VeVCDOrderReportDataDTO getOrder() {
		return order;
	}
	public void setOrder(VeVCDOrderReportDataDTO order) {
		this.order = order;
	}	
}
