package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;


public class UploadShippingCertificationExcelResultDTO extends BaseResultDTO{
	
	private BaseResultDTO[] validationerrors;
	private DODeliveryReportDataDTO[] dodeliveryreportdata;
	
	public BaseResultDTO[] getValidationerrors() {
		return validationerrors;
	}
	public void setValidationerrors(BaseResultDTO[] validationerrors) {
		this.validationerrors = validationerrors;
	}
	public DODeliveryReportDataDTO[] getDodeliveryreportdata() {
		return dodeliveryreportdata;
	}
	public void setDodeliveryreportdata(DODeliveryReportDataDTO[] dodeliveryreportdata) {
		this.dodeliveryreportdata = dodeliveryreportdata;
	}
	
		
}
