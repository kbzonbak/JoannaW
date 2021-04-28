package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvrDeliveryDetailReportResultDTO extends BaseResultDTO {

	private DvrDeliveryDetailDataDTO[] dvrdeliverydetaildata;
	private DvrDeliveryDetailTotalDataDTO total;
	private PageInfoDTO pageInfo;

	public DvrDeliveryDetailDataDTO[] getDvrdeliverydetaildata() {
		return dvrdeliverydetaildata;
	}

	public void setDvrdeliverydetaildata(DvrDeliveryDetailDataDTO[] dvrdeliverydetaildata) {
		this.dvrdeliverydetaildata = dvrdeliverydetaildata;
	}

	public DvrDeliveryDetailTotalDataDTO getTotal() {
		return total;
	}

	public void setTotal(DvrDeliveryDetailTotalDataDTO total) {
		this.total = total;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
