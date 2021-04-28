package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DdcOrderDeliveryStateReportResultDTO extends BaseResultDTO {

	private DdcOrderDeliveryStateDataDTO[] ddcdeliverystates;
	private PageInfoDTO pageInfo;
	
	public DdcOrderDeliveryStateDataDTO[] getDdcdeliverystates() {
		return ddcdeliverystates;
	}
	public void setDdcdeliverystates(DdcOrderDeliveryStateDataDTO[] ddcdeliverystates) {
		this.ddcdeliverystates = ddcdeliverystates;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
