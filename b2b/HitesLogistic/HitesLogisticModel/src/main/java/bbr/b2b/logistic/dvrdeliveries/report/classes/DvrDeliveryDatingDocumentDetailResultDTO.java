package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvrDeliveryDatingDocumentDetailResultDTO extends BaseResultDTO {

	private DvrDeliveryDatingDocumentDetailReportDataDTO[] dvrdeliverydatingdocumentdetailreportdata;
	private Integer totalreg;
	
	public DvrDeliveryDatingDocumentDetailReportDataDTO[] getDvrdeliverydatingdocumentdetailreportdata() {
		return dvrdeliverydatingdocumentdetailreportdata;
	}

	public void setDvrdeliverydatingdocumentdetailreportdata(
			DvrDeliveryDatingDocumentDetailReportDataDTO[] dvrdeliverydatingdocumentdetailreportdata) {
		this.dvrdeliverydatingdocumentdetailreportdata = dvrdeliverydatingdocumentdetailreportdata;
	}

	public Integer getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}

}
