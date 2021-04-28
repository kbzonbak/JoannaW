package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrDeliveryDetailExcelReportResultDTO extends BaseResultDTO {

	private DvrDeliveryDetailExcelDataDTO[] dvrorderdetails;

	public DvrDeliveryDetailExcelDataDTO[] getDvrorderdetails() {
		return dvrorderdetails;
	}

	public void setDvrorderdetails(DvrDeliveryDetailExcelDataDTO[] dvrorderdetails) {
		this.dvrorderdetails = dvrorderdetails;
	}
	
}
