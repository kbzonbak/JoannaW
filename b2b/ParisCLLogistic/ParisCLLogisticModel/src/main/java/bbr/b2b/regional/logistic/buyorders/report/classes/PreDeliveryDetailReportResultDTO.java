package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class PreDeliveryDetailReportResultDTO extends BaseResultDTO {

	private PreDeliveryDetailReportDataDTO[] predeliverydetail;
	private PreDeliveryDetailReportTotalDataDTO total;
	private PageInfoDTO pageInfo;	
	
	public PreDeliveryDetailReportTotalDataDTO getTotal() {
		return total;
	}

	public void setTotal(PreDeliveryDetailReportTotalDataDTO total) {
		this.total = total;
	}

	public PreDeliveryDetailReportDataDTO[] getPredeliverydetail() {
		return predeliverydetail;
	}

	public void setPredeliverydetail(PreDeliveryDetailReportDataDTO[] predeliverydetail) {
		this.predeliverydetail = predeliverydetail;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}	
}
