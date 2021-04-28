package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DODeliveryReportResultDTO extends BaseResultDTO {

	private DODeliveryReportDataDTO[] deliveryreport;
	private PageInfoDTO pageInfo;
	
	public DODeliveryReportDataDTO[] getDeliveryreport() {
		return deliveryreport;
	}
	public void setDeliveryreport(DODeliveryReportDataDTO[] deliveryreport) {
		this.deliveryreport = deliveryreport;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}	
}
