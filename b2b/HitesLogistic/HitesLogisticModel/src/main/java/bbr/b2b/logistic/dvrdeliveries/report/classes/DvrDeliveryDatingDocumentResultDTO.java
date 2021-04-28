package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvrDeliveryDatingDocumentResultDTO extends BaseResultDTO {

	private DvrDeliveryDatingDocumentReportDataDTO[] dvrdeliverydatingdocumentreportdata;
	private Integer totalreg;
	private PageInfoDTO pageInfo;

	public DvrDeliveryDatingDocumentReportDataDTO[] getDvrdeliverydatingdocumentreportdata() {
		return dvrdeliverydatingdocumentreportdata;
	}

	public void setDvrdeliverydatingdocumentreportdata(
			DvrDeliveryDatingDocumentReportDataDTO[] dvrdeliverydatingdocumentreportdata) {
		this.dvrdeliverydatingdocumentreportdata = dvrdeliverydatingdocumentreportdata;
	}

	public Integer getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
