package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DeliveryReportResultDTO extends BaseResultDTO {

	private DeliveryReportDataDTO[] deliveryreport;
	private PageInfoDTO pageInfo;
	
	public DeliveryReportDataDTO[] getDeliveryreport() {
		return deliveryreport;
	}
	public void setDeliveryreport(DeliveryReportDataDTO[] deliveryreport) {
		this.deliveryreport = deliveryreport;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}	
}
