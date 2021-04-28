package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;



public class UploadDODeliveryUpdatesResultDTO extends BaseResultDTO{

	private BaseResultDTO[] validationerrors;
	private DODeliveryReportDataDTO[] deliveryreportdata;
	
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}
	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
	public DODeliveryReportDataDTO[] getDeliveryreportdata() {
		return deliveryreportdata;
	}
	public void setDeliveryreportdata(DODeliveryReportDataDTO[] deliveryreportdata) {
		this.deliveryreportdata = deliveryreportdata;
	}	
}
