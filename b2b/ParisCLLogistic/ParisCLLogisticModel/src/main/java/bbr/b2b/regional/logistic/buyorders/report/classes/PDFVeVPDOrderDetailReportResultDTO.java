package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class PDFVeVPDOrderDetailReportResultDTO extends BaseResultDTO {

	private PDFVeVPDOrderDetailReportDataDTO[] orderdetail;

	private PageInfoDTO pageInfo;

	private PDFVeVPDOrderReportDataDTO order;

	public PDFVeVPDOrderDetailReportDataDTO[] getOrderdetail() {
		return orderdetail;
	}

	public void setOrderdetail(PDFVeVPDOrderDetailReportDataDTO[] orderdetail) {
		this.orderdetail = orderdetail;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

	public PDFVeVPDOrderReportDataDTO getOrder() {
		return order;
	}

	public void setOrder(PDFVeVPDOrderReportDataDTO order) {
		this.order = order;
	}

}
