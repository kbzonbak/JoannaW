package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class PreDeliveryDetailDataResultDTO extends BaseResultDTO {

	private PreDeliveryDetailDataDTO[] predeliverydetail;
	private PageInfoDTO pageInfo;
	
	public PreDeliveryDetailDataDTO[] getPredeliverydetail() {
		return predeliverydetail;
	}
	public void setPredeliverydetail(PreDeliveryDetailDataDTO[] predeliverydetail) {
		this.predeliverydetail = predeliverydetail;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}	
}
